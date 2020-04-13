package edu.byu.cs.tweeter.server.service;

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
    @Override
    public FollowerResponse getFollowers(FollowerRequest request) {
        FollowerDAO dao = new FollowerDAOImpl();
        return dao.getFollowers(request);
    }
}
