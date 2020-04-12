package edu.byu.cs.tweeter.server.lambda.feed;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.lambda.Handler;
import edu.byu.cs.tweeter.server.service.FeedServiceImpl;

/**
 * An AWS lambda function that returns the feed of a user.
 */
public class GetFeedHandler extends Handler implements RequestHandler<FeedRequest, FeedResponse>{

    /**
     * Returns the feed for the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the feed.
     */

    @Override
    public FeedResponse handleRequest(FeedRequest request, Context context){
        String alias = request.getUser().getAlias();
        String token = request.getToken();


        /*TODO: Remove after done testing*/
//        forTestingValidActiveToken(alias, token);

        //Check if user is logged in
        checkAuthorization(alias, token);



        FeedServiceImpl service = new FeedServiceImpl();
        FeedResponse response =  service.getFeed(request);

        /*Checks if an error occurred. If so, throw RuntimeException*/
        if(null != response.getMessage()){
            checkForError(response.getMessage());
        }


        /*Posting status succeeded, so update timestamp on the auth token*/
        updateAuthTimestamp(alias, token);

        return response;
    }
}
