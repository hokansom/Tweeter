package edu.byu.cs.tweeter.server.dao.following;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.server.json.Serializer;

public class FollowingDAOImpl implements FollowingDAO {
    private static final String TableName = "Follow";

    private static final String FollowerAliasAttr = "followerAlias";
    private static final String FolloweeAliasAttr = "followeeAlias";
    private static final String FolloweeAttr = "followee";


    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();


    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    @Override
    public FollowingResponse getFollowees(FollowingRequest request) {
        System.out.println("In FollowingDAOImpl");
        assert request.getLimit() > 0;
        assert request.getFollower() != null;

        String followerAlias = request.getFollower().getAlias();



        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#followerA", FollowerAliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":followerAlias", new AttributeValue().withS(followerAlias));

        try{
            QueryRequest queryRequest = new QueryRequest()
                    .withTableName(TableName)
                    .withKeyConditionExpression("#followerA = :followerAlias")
                    .withExpressionAttributeNames(attrNames)
                    .withExpressionAttributeValues(attrValues)
                    .withLimit(request.getLimit());

            if(isNonEmpty(request.getLastFollowee())){
                String lastFolloweeAlias = request.getLastFollowee().getAlias();
                Map<String, AttributeValue> startKey = new HashMap<>();
                startKey.put(FollowerAliasAttr, new AttributeValue().withS(followerAlias));
                startKey.put(FolloweeAliasAttr, new AttributeValue().withS(lastFolloweeAlias));

                queryRequest = queryRequest.withExclusiveStartKey(startKey);
            }

            QueryResult queryResult = amazonDynamoDB.query(queryRequest);

            List<User> followees = new ArrayList<>();
            List<Map<String, AttributeValue>> items = queryResult.getItems();
            if(items != null){
                for(Map<String, AttributeValue> item : items){
                    String followeeString = item.get(FolloweeAttr).getS();
                    User temp = Serializer.deserialize(followeeString, User.class);
                    followees.add(temp);
                }
            }

            boolean hasMorePages = false;
            Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
            if (lastKey != null) {
                hasMorePages = true;
            }

            return new FollowingResponse(followees, hasMorePages);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(String.format("[Internal Service Error]: Could not get @%s's followees", followerAlias));
        }
    }


    private static boolean isNonEmpty(User lastFollowee) {
        if(null != lastFollowee ){
            return (lastFollowee.getAlias() != null && lastFollowee.getAlias().length() > 0);
        }
        return false;
    }

}
