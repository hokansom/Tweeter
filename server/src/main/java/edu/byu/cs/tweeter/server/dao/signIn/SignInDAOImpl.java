package edu.byu.cs.tweeter.server.dao.signIn;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.server.service.AuthorizationServiceImpl;

public class SignInDAOImpl implements SignInDAO {
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
    public SignInResponse postSignIn(SignInRequest request) {
        String alias = request.getAlias();
        String password = request.getPassword();

        checkParameters(request);
        String message;
        Table table = dynamoDB.getTable(TableName);
        try{
            Item item = table.getItem(AliasAttr, alias);
            if(item == null){
                message = String.format("[Bad Request]: User with given alias (@%s) does not exist.", alias);
                return new SignInResponse(false, message);
            }

            String dbPassword = item.getString(PasswordAttr);
            if (!SignInDAO.validatePassword(password, dbPassword)){
                message = "[Bad Request]: Invalid password";
                return new SignInResponse(false, message);
            }

            User user = new User(item.getString(FirstNameAttr), item.getString(LastNameAtttr),
                    item.getString(AliasAttr), item.getString(ImageUrlAttr));

            return new SignInResponse(user, "");
        } catch (Exception e){
            e.printStackTrace();
            message = String.format("[Internal Service Error]: Could not sign in @%s", alias);
            throw new RuntimeException(message);
        }
    }

    private void checkParameters(SignInRequest request){
        if(null == request.getAlias() || request.getAlias().equals("")){
            throw new RuntimeException("[Bad Request]: Alias is required.");
        }
        if(null == request.getPassword() || request.getPassword().equals("")){
          throw new RuntimeException("[Bad Request]: Password is required.");
        }
    }


}
