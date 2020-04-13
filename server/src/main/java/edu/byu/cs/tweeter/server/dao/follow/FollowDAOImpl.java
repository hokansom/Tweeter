package edu.byu.cs.tweeter.server.dao.follow;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;


import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.server.json.Serializer;

public class FollowDAOImpl implements FollowDAO{
    private static final String TableName = "Follow";

    private static final String FollowerAliasAttr = "followerAlias";
    private static final String FolloweeAliasAttr = "followeeAlias";
    private static final String FolloweeAttr = "followee";
    private static final String FollowerAttr = "follower";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);


    @Override
    public FollowResponse postFollow(FollowRequest request) {
        String followerAlias = request.getFollow().getFollower().getAlias();
        String followeeAlias = request.getFollow().getFollowee().getAlias();
        checkInput(request);
        try{
            String followerString = Serializer.serialize(request.getFollow().getFollower());
            String followeeString = Serializer.serialize(request.getFollow().getFollowee());

            Table table = dynamoDB.getTable(TableName);


            Item item = new Item()
                    .withPrimaryKey(FollowerAliasAttr, followerAlias, FolloweeAliasAttr, followeeAlias)
                    .withString(FollowerAttr, followerString)
                    .withString(FolloweeAttr, followeeString);

            PutItemSpec putItemSpec = new PutItemSpec().withItem(item).withConditionExpression("attribute_not_exists(followeeAlias)");
            table.putItem(putItemSpec);

        } catch (ConditionalCheckFailedException e){
            throw new RuntimeException("[Bad Request]: follow relationship already exists");
        } catch (Exception e){
            throw new RuntimeException("[Internal Service Error]: could not add follow relationship");
        }

        return new FollowResponse(true, "");
    }

    private void checkInput(FollowRequest request){
        if(null == request.getFollow().getFollowee()){
            throw new RuntimeException("[Bad Request]: Followee cannot be null");
        }
        if(null == request.getFollow().getFollower()){
            throw new RuntimeException("[Bad Request]: Follower cannot be null ");
        }
        if(request.getFollow().getFollower().equals(request.getFollow().getFollowee())){
            throw new RuntimeException("[Bad Request]: User cannot follow themself ");
        }
    }

    @Override
    public boolean getFollow(String followerAlias, String followeeAlias) {
        Table table = dynamoDB.getTable(TableName);
        Item item = table.getItem(FollowerAliasAttr, followerAlias, FolloweeAliasAttr, followeeAlias);
        return item != null;

    }
}
