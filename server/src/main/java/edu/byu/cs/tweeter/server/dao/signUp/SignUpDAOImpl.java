package edu.byu.cs.tweeter.server.dao.signUp;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.server.dao.ImageDAO;
import edu.byu.cs.tweeter.server.service.AuthorizationServiceImpl;

public class SignUpDAOImpl implements SignUpDAO {
    private final String DEFAULT_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
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

        String message;

        Table table = dynamoDB.getTable(TableName);

        /*Check alias availability */
        Item item = table.getItem(AliasAttr, alias);
        if(item != null){
            System.out.println("Alias is already taken.");
            message = String.format("[Bad Request]: The alias (@%s) is already taken.", alias);
            return new SignUpResponse(false, message);
        }
        System.out.println("Valid alias");

        /*Hash password*/
        String hashed = SignUpDAO.hashPassword(password);


        /*Upload image to s3 and fetch url*/
        String imageUrl = DEFAULT_IMAGE_URL;
        if(!request.getImageString().equals("")){
            imageUrl = uploadImage(request.getImageString(), alias);
        }


        System.out.println("Successfully uploaded image");

        Item newItem = new Item()
                .withPrimaryKey(AliasAttr, alias)
                .withString(FirstNameAttr, request.getUser().getFirstName())
                .withString(LastNameAtttr, request.getUser().getLastName())
                .withString(PasswordAttr, hashed)
                .withString(ImageUrlAttr, imageUrl);

        table.putItem(newItem);


        newUser.setImageUrl(imageUrl);

        String token = getAuthorization(alias);

        return new SignUpResponse(newUser, token);
    }

    private String uploadImage(String imageString, String alias){
        ImageDAO imageDAO = new ImageDAO();
        return imageDAO.uploadImage(imageString, alias);
    }

    private String getAuthorization(String alias){
        AuthorizationServiceImpl authorizationService = new AuthorizationServiceImpl();
        return authorizationService.getAuthorization(alias);
    }



}
