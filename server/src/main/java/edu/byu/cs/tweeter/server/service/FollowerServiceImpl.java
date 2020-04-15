package edu.byu.cs.tweeter.server.service;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.server.dao.feed.FeedDAO;
import edu.byu.cs.tweeter.server.dao.feed.FeedDAOImpl;
import edu.byu.cs.tweeter.server.dao.followers.FollowerDAO;
import edu.byu.cs.tweeter.server.dao.followers.FollowerDAOImpl;
import edu.byu.cs.tweeter.server.dao.followers.FollowerDAOMock;


/**
 * Contains the business logic for getting the users following a user.
 */
public class FollowerServiceImpl implements FollowerService {

    @Override
    public FollowerResponse getFollowers(FollowerRequest request) {
        FollowerDAO dao = new FollowerDAOImpl();
        return dao.getFollowers(request);
    }

    public void getAllFollowers(Status status){
        String authorAlias = status.getAuthor().getAlias();
        System.out.println("Getting all individuals following @" + authorAlias);

        FollowerDAO dao = new FollowerDAOImpl();
        List<String> aliases =  dao.getAllFollowers(authorAlias);

        SQSService sqsService = new SQSService();
        sqsService.postToFeedQueue(aliases, status);

    }

}
