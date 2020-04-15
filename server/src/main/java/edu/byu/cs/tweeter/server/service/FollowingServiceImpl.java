package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.following.FollowingDAO;
import edu.byu.cs.tweeter.server.dao.following.FollowingDAOImpl;
import edu.byu.cs.tweeter.server.dao.following.FollowingDAOMock;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceImpl implements FollowingService {

    @Override
    public FollowingResponse getFollowees(FollowingRequest request) {

        FollowingDAO dao = new FollowingDAOImpl();
        return dao.getFollowees(request);
    }
}
