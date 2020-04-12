package edu.byu.cs.tweeter.server.lambda.status;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.server.lambda.Handler;
import edu.byu.cs.tweeter.server.service.AuthorizationServiceImpl;
import edu.byu.cs.tweeter.server.service.StatusServiceImpl;

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


        /*TODO: Remove after done testing*/
//        forTestingValidActiveToken(alias, token);

        checkAuthorization(alias, token);



        //2. Call StatusServiceImpl or StoryService? and update story

        //3. Write post status message
        StatusServiceImpl service = new StatusServiceImpl();
        StatusResponse response = service.postStatus(request);

        /*Checks if an error occurred. If so, throw RuntimeException*/
        checkForError(response.getMessage());


        /*Posting status succeeded, so update timestamp on the auth token*/
        updateAuthTimestamp(alias, token);

        return response;
    }
}
