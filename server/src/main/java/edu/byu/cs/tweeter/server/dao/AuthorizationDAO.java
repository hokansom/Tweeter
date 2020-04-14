package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.*;

import java.util.Date;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.Authorization;

public class AuthorizationDAO {
    private static final String TableName = "Authorization";

    private static final String AliasAttr = "alias";
    private static final String TokenAttr = "token";
    private static final String TimeStampAttr = "timeStamp";

    private static final int TimeDifference = 1800000; //30 minutes timeout

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);


    public boolean checkAuthorization(String alias, String token){
        Authorization dbAuth = getAuthorization(alias, token);
        if(dbAuth == null){
            System.out.println("database returned null");
            throw new RuntimeException("[Unauthorized]: User is unauthorized");
        }
        Date date = new Date();
        long currentTime = date.getTime();
        System.out.println(String.format("Current time: %d", currentTime));
        System.out.println(String.format("DB time: %d", dbAuth.getTimeStamp()));
        return (dbAuth.getTimeStamp() > (currentTime - TimeDifference));
    }

    private Authorization getAuthorization(String alias, String token){
        System.out.println(alias);
        System.out.println(token);
        try{
            Table table = dynamoDB.getTable(TableName);
            System.out.println("Got table");
            Item item = table.getItem(AliasAttr, alias, TokenAttr, token);
            if(item == null){
                System.out.println("Got null");
                return null;

            }else{
                System.out.println("Got something");
                return new Authorization(item.getString(AliasAttr), item.getString(TokenAttr), item.getLong(TimeStampAttr));
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("[Internal Service Error]: Could not verify user is authorized");
        }

    }

    public void updateAuthorization(String alias, String token){
        System.out.println(alias);
        System.out.println(token);
        Table table = dynamoDB.getTable(TableName);
        try{
            Date date = new Date();
            long current = date.getTime();
            System.out.println(String.format("New time: %d", current));
            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(AliasAttr, alias, TokenAttr, token)
                    .withUpdateExpression("set #timeStamp = :time")
                    .withNameMap(new NameMap().with("#timeStamp", "timeStamp"))
                    .withValueMap(new ValueMap().withNumber(":time", current));
            table.updateItem(updateItemSpec);
            System.out.println("Updated table");
        }
        catch (Exception e){
            throw new RuntimeException("[Internal Service Error]: Something went wrong while updating auth timestamp");
        }
    }

    public String getAuthorization(String alias){
        String token = UUID.randomUUID().toString();
        Date date = new Date();
        long current = date.getTime();
        try{
            Table table = dynamoDB.getTable(TableName);

            Item item = new Item()
                    .withPrimaryKey(AliasAttr, alias, TokenAttr, token)
                    .withNumber(TimeStampAttr, current);
            table.putItem(item);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("[Internal Service Error]:  Could not get auth token for user");
        }



        return token;
    }
}
