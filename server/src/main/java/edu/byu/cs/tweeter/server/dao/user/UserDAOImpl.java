package edu.byu.cs.tweeter.server.dao.user;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.server.dao.follow.FollowDAOImpl;

public class UserDAOImpl implements UserDAO {
    private final static String DEFAULT_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final String FILLER_PASSWORD = "1000:846dbda73615bf594aec70f79bc34221:1d0e75d5f60003bc4158a580e5e7e0ca0ed3d0470657dfc4e5728ef80df1e66995de4d7548aaf22224ac4bfd402853fcec1bb6b90e93780c891183205948ae0c";

    private static final String TableName = "User";

    private static final String AliasAttr = "alias";
    private static final String FirstNameAttr = "firstName";
    private static final String LastNameAtttr = "lastName";
    private static final String ImageUrlAttr = "imageUrl";
    private static final String PasswordAttr =  "password";



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

    public void addUserBatch(List<User> users){
        // Constructor for TableWriteItems takes the name of the table, which I have stored in TABLE_USER
        TableWriteItems items = new TableWriteItems(TableName);

        // Add each user into the TableWriteItems object
        for (User user : users) {
            Item item = new Item()
                    .withPrimaryKey(AliasAttr, user.getAlias())
                    .withString(FirstNameAttr, user.getFirstName())
                    .withString(LastNameAtttr, user.getLastName())
                    .withString(ImageUrlAttr, user.getImageUrl())
                    .withString(PasswordAttr, FILLER_PASSWORD);

            items.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems(TableName);
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }
    }

    private void loopBatchWrite(TableWriteItems items) {

        // The 'dynamoDB' object is of type DynamoDB and is declared statically in this example
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);
        System.out.println("Wrote batch");

        // Check the outcome for items that didn't make it onto the table
        // If any were not added to the table, try again to write the batch
        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
            System.out.println("Wrote more Users");
        }
    }
}
