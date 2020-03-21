package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.FollowDAO;

/**
 * Contains the business logic for posting a follow relationship.
 */
public class FollowServiceImpl implements FollowService {

    @Override
    public FollowResponse postFollow(FollowRequest request) {
        FollowDAO dao = new FollowDAO();
        return dao.postFollow(request);
    }



}