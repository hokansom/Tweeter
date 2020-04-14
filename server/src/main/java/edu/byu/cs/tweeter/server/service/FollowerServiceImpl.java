package edu.byu.cs.tweeter.server.service;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.server.dao.followers.FollowerDAO;
import edu.byu.cs.tweeter.server.dao.followers.FollowerDAOImpl;
import edu.byu.cs.tweeter.server.dao.followers.FollowerDAOMock;


/**
 * Contains the business logic for getting the users following a user.
 */
public class FollowerServiceImpl implements FollowerService {
    private final int limit = 25; // Single read from DB returns 25 items
    private final int totalLimit = limit * 10; //Batches can send 10 messages at a time
    @Override
    public FollowerResponse getFollowers(FollowerRequest request) {
        FollowerDAO dao = new FollowerDAOImpl();
        return dao.getFollowers(request);
    }

    public void getAllFollowers(Status status){

        User followee = status.getAuthor();
        String alias = status.getAuthor().getAlias();
        System.out.println("Getting all individuals following @" + alias);
        SQSService service = new SQSService();

        FollowerDAO dao = new FollowerDAOImpl();
        FollowerRequest request = new FollowerRequest(followee, limit, null);
        FollowerResponse response = dao.getFollowers(request);
        List<String> aliases = new ArrayList<>();

        while(null != response){
            System.out.println("Response is not null. Getting followers");
            List<User> users = response.getFollowers();

            for(User user: users){
                System.out.println(user.getAlias()); 
                aliases.add(user.getAlias());
                if(aliases.size() == totalLimit){
                    System.out.println("Preparing to send over to postToFeedQueue");
                    service.postToFeedQueue(aliases, status);
                    aliases = new ArrayList<>();
                }
            }
            if(response.getHasMorePages()){
                User last = users.get(users.size() - 1);
                request = new FollowerRequest(followee, limit, last);
                response = dao.getFollowers(request);
            } else {
                response = null;
            }

        }
        if(aliases.size() > 0){
            System.out.println("Preparing to send over to postToFeedQueue");
            service.postToFeedQueue(aliases, status);
        }
    }

}
