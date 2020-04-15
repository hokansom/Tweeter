package edu.byu.cs.tweeter.server.dao.unfollow;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class UnfollowDAOImpl implements UnfollowDAO {
    private static final String TableName = "Follow";

    private static final String FollowerAliasAttr = "followerAlias";
    private static final String FolloweeAliasAttr = "followeeAlias";



    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);


    @Override
    public FollowResponse deleteFollow(FollowRequest request) {
        Table table = dynamoDB.getTable(TableName);

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(FollowerAliasAttr, request.getFollow().getFollower().getAlias(), FolloweeAliasAttr, request.getFollow().getFollowee().getAlias())
                .withConditionExpression("attribute_exists(followeeAlias)");
        try{
            table.deleteItem(deleteItemSpec);

        } catch (ConditionalCheckFailedException e){
            throw new RuntimeException("[Bad Request]: Cannot delete a follow relationship that doesn't exist");

        } catch (Exception e){
            throw new RuntimeException("[Internal Service Error]: Could not remove follow");
        }

        return new FollowResponse(true, "");

    }
}
