package edu.byu.cs.tweeter.server.lambda.search;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.server.lambda.Handler;
import edu.byu.cs.tweeter.server.service.SearchUserServiceImpl;

/**
 * An AWS lambda function that returns a user with the given alias.
 */
public class GetUserHandler extends Handler implements RequestHandler<SearchRequest, SearchResponse> {
    /**
     * Returns the user with the given alias.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the user.
     */
    @Override
    public SearchResponse handleRequest(SearchRequest request, Context context) {

        SearchUserServiceImpl service = new SearchUserServiceImpl();
        SearchResponse response = service.getUser(request);

        /*FIXME: the line below may not be needed*/
        checkForError(response.getMessage());
        return response;
    }
}
