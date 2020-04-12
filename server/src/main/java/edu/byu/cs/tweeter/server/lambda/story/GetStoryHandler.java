package edu.byu.cs.tweeter.server.lambda.story;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.lambda.Handler;
import edu.byu.cs.tweeter.server.service.StoryServiceImpl;

/**
 * An AWS lambda function that returns a user's story.
 */
public class GetStoryHandler extends Handler implements RequestHandler<StoryRequest, StoryResponse> {
    /**
     * Returns the story for the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the story.
     */
    @Override
    public StoryResponse handleRequest(StoryRequest request, Context context) {
        StoryServiceImpl service = new StoryServiceImpl();
        StoryResponse response = service.getStory(request);

        checkForError(response.getMessage());

        return response;
    }
}
