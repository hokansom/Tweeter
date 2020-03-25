package edu.byu.cs.tweeter.server.lambda.status;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.server.service.StatusServiceImpl;

/**
 * An AWS lambda function that posts a new status.
 */
public class PostStatusHandler implements RequestHandler<StatusRequest, StatusResponse> {


    /**
     * Posts a new status to the database. Returns if the post succeeded or not
     *
     * @param request contains the data required to fulfill the request.
     * @return success
     *
     */
    @Override
    public StatusResponse handleRequest(StatusRequest request, Context context) {
        StatusServiceImpl service = new StatusServiceImpl();
        return service.postStatus(request, "");
    }
}
