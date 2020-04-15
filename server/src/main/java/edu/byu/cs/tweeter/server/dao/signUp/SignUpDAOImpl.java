package edu.byu.cs.tweeter.server.dao.signUp;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.server.dao.ImageDAO;
import edu.byu.cs.tweeter.server.service.AuthorizationServiceImpl;

public class SignUpDAOImpl implements SignUpDAO {

    private static final String TableName = "User";

    private static final String AliasAttr = "alias";
    private static final String FirstNameAttr = "firstName";
    private static final String LastNameAtttr = "lastName";
    private static final String ImageUrlAttr = "imageUrl";
    private static final String PasswordAttr = "password";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public SignUpResponse postSignUp(SignUpRequest request) {
        String alias = request.getUser().getAlias();
        String password = request.getPassword();
        User newUser = request.getUser();
        try{
            Table table = dynamoDB.getTable(TableName);


            /*Hash password*/
            String hashed = SignUpDAO.hashPassword(password);

            Item newItem = new Item()
                    .withPrimaryKey(AliasAttr, alias)
                    .withString(FirstNameAttr, request.getUser().getFirstName())
                    .withString(LastNameAtttr, request.getUser().getLastName())
                    .withString(PasswordAttr, hashed)
                    .withString(ImageUrlAttr, request.getUser().getImageUrl());

            PutItemSpec putItemSpec = new PutItemSpec()
                    .withItem(newItem)
                    .withConditionExpression("attribute_not_exists(alias)");

            table.putItem(putItemSpec);


            return new SignUpResponse(newUser, "");

        } catch (ConditionalCheckFailedException e){
            String message = String.format("[Bad Request]: The alias (@%s) is already taken.", alias);
            throw new RuntimeException(message);

        } catch (Exception e){
            e.printStackTrace();
            String message = String.format("[Internal Service Error]: Could not sign up @%s", alias);
            throw new RuntimeException(message);
        }
    }



}
