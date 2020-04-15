package edu.byu.cs.tweeter.server.dao.signOut;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.*;


import edu.byu.cs.tweeter.model.service.request.SignOutRequest;

public class SignOutDAOImpl implements SignOutDAO {
    private static final String TableName = "Authorization";

    private static final String AliasAttr = "alias";
    private static final String TokenAttr = "token";


    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public void signOut(SignOutRequest request) {
        Table table = dynamoDB.getTable(TableName);
        try{
            long current = 0;
            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(AliasAttr, request.getAlias(), TokenAttr, request.getToken())
                    .withUpdateExpression("set #timeStamp = :time")
                    .withNameMap(new NameMap().with("#timeStamp", "timeStamp"))
                    .withValueMap(new ValueMap().withNumber(":time", current));
            table.updateItem(updateItemSpec);
            System.out.println("Deactivated user's auth token");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("[Internal Service Error]: Something went wrong while deactivating token");
        }
    }
}
