package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

/**
 * Defines the interface for the 'follow' service.
 */
public interface UnfollowService {
    /**
     * Deletes a follow relationship.
     * Deletes a follow relationship.
     *
     * @param request contains the data required to fulfill the request.
     * @return success
     */
    FollowResponse deleteFollow(FollowRequest request) throws IOException;
}
