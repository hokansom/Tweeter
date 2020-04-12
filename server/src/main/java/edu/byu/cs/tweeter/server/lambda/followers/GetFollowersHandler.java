package edu.byu.cs.tweeter.server.lambda.followers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.server.lambda.Handler;
import edu.byu.cs.tweeter.server.service.FollowerServiceImpl;

/**
 * An AWS lambda function that returns the users following a user.
 */
public class GetFollowersHandler extends Handler implements RequestHandler<FollowerRequest, FollowerResponse> {
    /**
     * Returns the users that are following the user specified in the request. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followers.
     */
    @Override
    public FollowerResponse handleRequest(FollowerRequest request, Context context) {
        /*TODO: low priority, but update token timestamp*/

        FollowerServiceImpl service = new FollowerServiceImpl();
        FollowerResponse response = service.getFollowers(request);

        if(null != response.getMessage()){
            checkForError(response.getMessage());
        }

        return response;
    }

}
