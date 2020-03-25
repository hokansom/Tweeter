package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

/**
 * Defines the interface for the 'follow' service.
 */
public interface FollowService {
    /**
     * Updates a follow relationship. Uses information in
     * the request object to either create the follow relationship between the followee and follower,
     * or delete the follow relationship between the followee and follower.
     *
     * @param request contains the data required to fulfill the request.
     * @return success
     */
    FollowResponse postFollow(FollowRequest request, String auth) throws IOException;
}
