package edu.byu.cs.tweeter.server.lambda.follow;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.server.lambda.Handler;
import edu.byu.cs.tweeter.server.service.FollowServiceImpl;

/**
 * An AWS lambda function that returns the users a user is following.
 */
public class FollowHandler extends Handler implements RequestHandler<FollowRequest, FollowResponse> {

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

        String alias = request.getFollow().getFollower().getAlias();
        String token = request.getToken();


        /*TODO: Remove after done testing*/
//        forTestingValidActiveToken(alias, token);

        checkAuthorization(alias, token);
        //TODO: Separate into follow and unfollow

        //Check if user is logged in an authorized, then proceed

        System.out.println(request.getFollow().followee.getAlias());
        System.out.println(request.getFollow().follower.getAlias());
        System.out.println(String.format("Request token: %s", request.getToken()));

        FollowServiceImpl service = new FollowServiceImpl();
        FollowResponse response = service.postFollow(request);

        if(null != response.getMessage()){
            checkForError(response.getMessage());
        }

        updateAuthTimestamp(alias, token);

        return response;
    }
}
