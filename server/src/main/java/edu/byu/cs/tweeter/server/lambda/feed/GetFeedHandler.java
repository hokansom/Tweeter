package edu.byu.cs.tweeter.server.lambda.feed;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.service.FeedServiceImpl;

/**
 * An AWS lambda function that returns the feed of a user.
 */
public class GetFeedHandler implements RequestHandler<FeedRequest, FeedResponse>{

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
        FeedServiceImpl service = new FeedServiceImpl();
        return service.getFeed(request);
    }
}
