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


        String message = checkParameters(request);
        if (null != message){
            return new SignInResponse(false, message);
        }

        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(AliasAttr, alias);
        if(item == null){
            System.out.println("Got null");
            message = String.format("[Bad Request]: User with given alias (@%s) does not exist.", alias);
            return new SignInResponse(false, message);
        }
        System.out.println("Found a user");

        String dbPassword = item.getString(PasswordAttr);
        if (!SignInDAO.validatePassword(password, dbPassword)){
            message = "[Bad Request]: Invalid password";
            return new SignInResponse(false, message);
        }

        User user = new User(item.getString(FirstNameAttr), item.getString(LastNameAtttr),
                item.getString(AliasAttr), item.getString(ImageUrlAttr));

        String token = getAuthorization(alias);

        return new SignInResponse(user, token);

    }

    private String checkParameters(SignInRequest request){
        if(null == request.getAlias() || request.getAlias().equals("")){
            return "[Bad Request]: Alias is required.";
        }
        if(null == request.getPassword() || request.getPassword().equals("")){
            return "[Bad Request]: Password is required.";
        }
        return null;
    }

    private String getAuthorization(String alias){
        AuthorizationServiceImpl authorizationService = new AuthorizationServiceImpl();
        return authorizationService.getAuthorization(alias);
    }
}
