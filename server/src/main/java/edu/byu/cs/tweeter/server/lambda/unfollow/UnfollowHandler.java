package edu.byu.cs.tweeter.server.lambda.unfollow;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.server.lambda.Handler;
import edu.byu.cs.tweeter.server.service.FollowServiceImpl;
import edu.byu.cs.tweeter.server.service.UnfollowServiceImpl;

/**
 * An AWS lambda function that returns the users a user is following.
 */
public class UnfollowHandler extends Handler implements RequestHandler<FollowRequest, FollowResponse> {

    /**
     * Deletes a follow relationship.
     *
     * @param request contains the data required to fulfill the request.
     * @return success
     */
    @Override
    public FollowResponse handleRequest(FollowRequest request, Context context) {

        String alias = request.getFollow().getFollower().getAlias();
        String token = request.getToken();


        /*TODO: Remove after done testing*/
//        forTestingValidActiveToken(alias, token);

        checkAuthorization(alias, token);

        //Check if user is logged in an authorized, then proceed

        System.out.println(request.getFollow().followee.getAlias());
        System.out.println(request.getFollow().follower.getAlias());
        System.out.println(String.format("Request token: %s", request.getToken()));

        UnfollowServiceImpl service = new UnfollowServiceImpl();
        FollowResponse response = service.deleteFollow(request);

        if(null != response.getMessage()){
            checkForError(response.getMessage());
        }

        updateAuthTimestamp(alias, token);

        return response;
    }
}