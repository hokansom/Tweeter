package edu.byu.cs.tweeter.server.dao.feed;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Feed;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.json.Serializer;

public class FeedDAOImpl implements FeedDAO {
    private static final String TableName = "Feed";

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
    public FeedResponse getFeed(FeedRequest request) {
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
            if(items != null){
                for(Map<String, AttributeValue> item : items){
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

            Feed feed = new Feed(statuses, request.getUser());
            return new FeedResponse(feed, hasMorePages);

        } catch (Exception e){
            e.printStackTrace();
            String message = String.format("[Internal Service Error]: could not get @%s's feed", authorAlias);
            throw new RuntimeException(message);
        }

    }

    private static boolean isNonEmpty(Status lastStatus) {
        if(null != lastStatus ){
            return (lastStatus.getDate() != 0);
        }
        return false;
    }

    public void updateFeeds(Status status, List<String> aliases){
        System.out.println("Update feeds for status");
        long date = status.getDate();
        String statusString = Serializer.serialize(status);
        TableWriteItems items = new TableWriteItems(TableName);

        for(String alias: aliases){
            Item item = new Item()
                    .withPrimaryKey(AliasAttr, alias, DateAttr, date)
                    .withString(StatusAttr, statusString);
            items.addItemToPut(item);

            if(items.getItemsToPut() != null && items.getItemsToPut().size() == 25){
                loopBatchWrite(items);
                items = new TableWriteItems(TableName);
            }
        }

        if(items.getItemsToPut() != null && items.getItemsToPut().size() > 0){
            loopBatchWrite(items);
        }
    }

    private void loopBatchWrite(TableWriteItems items) {

        try{
            BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);
            System.out.println("Wrote feed batch");

            // Check the outcome for items that didn't make it onto the table
            // If any were not added to the table, try again to write the batch
            while (outcome.getUnprocessedItems().size() > 0) {
                Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
                outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("[Internal Service Error]: Could not update feeds after a new status was posted");
        }

    }
}
