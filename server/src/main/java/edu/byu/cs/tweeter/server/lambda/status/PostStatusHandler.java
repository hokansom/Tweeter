package edu.byu.cs.tweeter.server.lambda.status;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.server.lambda.Handler;
import edu.byu.cs.tweeter.server.service.AuthorizationServiceImpl;
import edu.byu.cs.tweeter.server.service.StatusServiceImpl;
import edu.byu.cs.tweeter.server.service.StoryServiceImpl;

/**
 * An AWS lambda function that posts a new status.
 */
public class PostStatusHandler extends Handler implements RequestHandler<StatusRequest, StatusResponse>  {


    /**
     * Posts a new status to the database. Returns if the post succeeded or not
     *
     * @param request contains the data required to fulfill the request.
     * @return success
     *
     */
    @Override
    public StatusResponse handleRequest(StatusRequest request, Context context) {
        String alias = request.getAuthor().getAlias();
        String token = request.getToken();


        checkAuthorization(alias, token);


        StoryServiceImpl service = new StoryServiceImpl();
        StatusResponse response = service.postStatus(request);

        /*Checks if an error occurred. If so, throw RuntimeException*/
        if(null != response.getMessage()){
            checkForError(response.getMessage());
        }

        //Post to queue
        StatusServiceImpl statusService = new StatusServiceImpl();
        statusService.addStatusToQueue(request.getStatus());

        /*Posting status succeeded, so update timestamp on the auth token*/
        updateAuthTimestamp(alias, token);

        return response;
    }
}
