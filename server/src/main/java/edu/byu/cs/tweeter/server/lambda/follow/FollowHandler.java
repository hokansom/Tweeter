package edu.byu.cs.tweeter.server.lambda.follow;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.server.service.FollowServiceImpl;

/**
 * An AWS lambda function that returns the users a user is following.
 */
public class FollowHandler implements RequestHandler<FollowRequest, FollowResponse> {

    /**
     * Updates a follow relationship. Uses information in
     * the request object to either create the follow relationship between the followee and follower,
     * or delete the follow relationship between the followee and follower.
     *
     * @param request contains the data required to fulfill the request.
     * @return success
     */
    @Override
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        FollowServiceImpl service = new FollowServiceImpl();
        return service.postFollow(request);
    }
}
