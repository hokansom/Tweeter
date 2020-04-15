package edu.byu.cs.tweeter.server.dao.user;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.server.dao.follow.FollowDAOImpl;

public class UserDAOImpl implements UserDAO {
    private static final String TableName = "User";

    private static final String AliasAttr = "alias";
    private static final String FirstNameAttr = "firstName";
    private static final String LastNameAtttr = "lastName";
    private static final String ImageUrlAttr = "imageUrl";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
    /**
     * Gets the user from the database that has the specified alias from the request.
     *
     * @param request contains information about the user whose is being retrieved.
     * @return the user.
     */
    @Override
    public SearchResponse getUser(SearchRequest request) {
        try{
            Table table = dynamoDB.getTable(TableName);

            Item item = table.getItem(AliasAttr, request.getAlias());
            if(item == null){
                String message = String.format("[Bad Request]: User with given alias (@%s) does not exist", request.getAlias());
                return new SearchResponse(false, message);
            }

            User user = new User(item.getString(FirstNameAttr), item.getString(LastNameAtttr),
                    item.getString(AliasAttr), item.getString(ImageUrlAttr));

            return new SearchResponse(true, "", user, false);
        } catch (Exception e){
            e.printStackTrace();
            String message = String.format("[Internal Service Error]: Could not get @%s", request.getAlias());
            throw new RuntimeException(message);
        }
    }
}
