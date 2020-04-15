package edu.byu.cs.tweeter.server.dao.followers;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.server.json.Serializer;

public class FollowerDAOImpl implements FollowerDAO {
    private static final String TableName = "Follow";
    private static final String IndexName = "followeeAlias-followerAlias-index";

    private static final String FollowerAliasAttr = "followerAlias";
    private static final String FolloweeAliasAttr = "followeeAlias";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    /**
     * Gets the users from the database that are following the user specified in the request. Uses
     * information in the request object to limit the number of followers returned and to return the
     * next set of followers after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the followers.
     */
    @Override
    public FollowerResponse getFollowers(FollowerRequest request) {

        assert request.getLimit() > 0;
        assert request.getFollowee() != null;

        String followeeAlias = request.getFollowee().getAlias();

        Table table = dynamoDB.getTable(TableName);

        NameMap nameMap = new NameMap()
                .with("#followeeA", FolloweeAliasAttr);

        ValueMap valueMap = new ValueMap()
                .withString(":followeeAlias", followeeAlias);
        Index index = table.getIndex(IndexName);

        int limit = request.getLimit();
        try{
            QuerySpec querySpec = new QuerySpec()
                    .withKeyConditionExpression("#followeeA = :followeeAlias")
                    .withNameMap(nameMap)
                    .withValueMap(valueMap)
                    .withMaxResultSize(limit);

            if(isNonEmpty(request.getLastFollower())){
                String lastFollowerAlias = request.getLastFollower().getAlias();
                querySpec.withExclusiveStartKey(FolloweeAliasAttr,followeeAlias, FollowerAliasAttr,lastFollowerAlias);
            }

            ItemCollection<QueryOutcome> items = index.query(querySpec);
            Iterator<Item> iterator = items.iterator();

            List<User> followers = new ArrayList<>();
            while(iterator.hasNext()){
                String result = iterator.next().toJSONPretty();
                DBResults temp = Serializer.deserialize(result, DBResults.class);
                User follower = Serializer.deserialize(temp.getFollower(), User.class);
                followers.add(follower);
            }
            boolean hasMorePages = followers.size() == request.getLimit();

            return new FollowerResponse(followers, hasMorePages);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(String.format("[Internal Service Error]: Could not get @%s's followers", followeeAlias));
        }

   }

   public List<String> getAllFollowers(String alias){
      if(alias.length() == 0){
          throw new RuntimeException("[Bad Request]: Alias was empty");
      }
      try{
          Table table = dynamoDB.getTable(TableName);
          NameMap nameMap = new NameMap()
                  .with("#followeeA", FolloweeAliasAttr);

          ValueMap valueMap = new ValueMap()
                  .withString(":followeeAlias", alias);
          Index index = table.getIndex(IndexName);

          QuerySpec querySpec = new QuerySpec()
                  .withKeyConditionExpression("#followeeA = :followeeAlias")
                  .withNameMap(nameMap)
                  .withValueMap(valueMap);

          ItemCollection<QueryOutcome> items = index.query(querySpec);
          Iterator<Item> iterator = items.iterator();

          List<String> followers = new ArrayList<>();
          while(iterator.hasNext()){
              String result = iterator.next().toJSONPretty();
              DBResults temp = Serializer.deserialize(result, DBResults.class);
              User follower = Serializer.deserialize(temp.getFollower(), User.class);
              followers.add(follower.getAlias());
          }
          return followers;

      } catch (Exception e){
          e.printStackTrace();
          throw new RuntimeException(String.format("[Internal Service Error]: Could not get all of @%s's followers", alias));
      }

   }


    private static boolean isNonEmpty(User lastFollower) {
        if(null != lastFollower ){
            return (lastFollower.getAlias() != null && lastFollower.getAlias().length() > 0);
        }
        return false;
    }

    public class DBResults implements Serializable {
        private String followeeAlias;
        private String followerAlias;
        private String followee;
        private String follower;

        public DBResults(){}

        public String getFolloweeAlias() {
            return followeeAlias;
        }

        public void setFolloweeAlias(String followeeAlias) {
            this.followeeAlias = followeeAlias;
        }

        public String getFollowerAlias() {
            return followerAlias;
        }

        public void setFollowerAlias(String followerAlias) {
            this.followerAlias = followerAlias;
        }

        public String getFollowee() {
            return followee;
        }

        public void setFollowee(String followee) {
            this.followee = followee;
        }

        public String getFollower() {
            return follower;
        }

        public void setFollower(String follower) {
            this.follower = follower;
        }
    }


}
