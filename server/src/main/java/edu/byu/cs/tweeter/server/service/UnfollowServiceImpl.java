package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.unfollow.UnfollowDAO;
import edu.byu.cs.tweeter.server.dao.unfollow.UnfollowDAOImpl;

/**
 * Contains the business logic for posting a follow relationship.
 */
public class UnfollowServiceImpl implements UnfollowService {

    @Override
    public FollowResponse deleteFollow(FollowRequest request) {
        UnfollowDAO dao = new UnfollowDAOImpl();
        return dao.deleteFollow(request);
    }



}
