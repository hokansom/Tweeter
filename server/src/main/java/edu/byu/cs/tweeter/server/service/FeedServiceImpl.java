package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.feed.FeedDAO;
import edu.byu.cs.tweeter.server.dao.feed.FeedDAOImpl;
import edu.byu.cs.tweeter.server.dao.feed.FeedDAOMock;

/**
 * Contains the business logic for getting the feed of a user.
 */
public class FeedServiceImpl implements FeedService {
    @Override
    public FeedResponse getFeed(FeedRequest request){
        FeedDAO dao = new FeedDAOImpl();
        return dao.getFeed(request);
    }

    public void updateFeeds(Status status, List<String> aliases){
        System.out.println("In FeedServiceImpl");
        FeedDAO dao = new FeedDAOImpl();
        dao.updateFeeds(status, aliases);
    }
}
