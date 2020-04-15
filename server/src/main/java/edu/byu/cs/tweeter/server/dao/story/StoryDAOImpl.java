package edu.byu.cs.tweeter.server.dao.story;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.json.Serializer;

public class StoryDAOImpl implements StoryDAO {
    private static final String TableName = "Story";

    private static final String AliasAttr = "alias";
    private static final String StatusAttr = "status";
    private static final String DateAttr = "date";


    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public StoryResponse getStory(StoryRequest request) {
        assert request.getLimit() > 0;
        assert request.getLastStatus() != null;

        String authorAlias = request.getUser().getAlias();

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#alias", AliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":alias", new AttributeValue().withS(authorAlias));

        try{
            QueryRequest queryRequest = new QueryRequest()
                    .withTableName(TableName)
                    .withKeyConditionExpression("#alias = :alias")
                    .withExpressionAttributeNames(attrNames)
                    .withExpressionAttributeValues(attrValues)
                    .withScanIndexForward(false)
                    .withLimit(request.getLimit());

            if(isNonEmpty(request.getLastStatus())){
                long lastStatusTime = request.getLastStatus().getDate();

                String dateString = String.format("%d", lastStatusTime);
                Map<String, AttributeValue> startKey = new HashMap<>();
                startKey.put(AliasAttr, new AttributeValue().withS(authorAlias));
                startKey.put(DateAttr, new AttributeValue().withN(dateString));

                queryRequest = queryRequest.withExclusiveStartKey(startKey);
            }

            QueryResult queryResult = amazonDynamoDB.query(queryRequest);
            List<Status> statuses = new ArrayList<>();

            List<Map<String, AttributeValue>> items = queryResult.getItems();
            if(items != null) {
                for (Map<String, AttributeValue> item : items) {
                    String status = item.get(StatusAttr).getS();
                    Status temp = Serializer.deserialize(status, Status.class);
                    statuses.add(temp);
                }
            }

            boolean hasMorePages = false;
            Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
            if (lastKey != null) {
                hasMorePages = true;
            }

            Story story = new Story(statuses, request.getUser());
            return new StoryResponse(story, hasMorePages);

        } catch (Exception e){
            e.printStackTrace();
            String message = String.format("[Internal Service Error]: could not get @%s's story", authorAlias);
            throw new RuntimeException(message);
        }
    }


    @Override
    public StatusResponse postStatus(StatusRequest request) {
        try{
            String alias = request.getAuthor().getAlias();
            String statusString = Serializer.serialize(request.getStatus());
            long timeStamp = request.getStatus().getDate();

            Table table = dynamoDB.getTable(TableName);
            Item item = new Item()
                    .withPrimaryKey(AliasAttr, alias, DateAttr, timeStamp)
                    .withString(StatusAttr, statusString);
            PutItemSpec putItemSpec = new PutItemSpec()
                    .withItem(item)
                    .withConditionExpression("attribute_not_exists(statusString)");

            table.putItem(putItemSpec);

        } catch (ConditionalCheckFailedException e){
            e.printStackTrace();
            throw new RuntimeException("[Bad Request]: Status has already been posted");
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("[Internal Service Error]: Could not post status");
        }

        return new StatusResponse(true, "");
    }

    private static boolean isNonEmpty(Status lastStatus) {
        if(null != lastStatus ){
            return (lastStatus.getDate() != 0);
        }
        return false;
    }
}
