package edu.byu.cs.tweeter.server.lambda.signIn;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.server.service.SignInServiceImpl;

/**
 * An AWS lambda function that returns the user with the specified alias.
 */
public class PostSignInHandler implements RequestHandler<SignInRequest, SignInResponse> {
    /**
     * Returns the user object and auth token for the given alias specified in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    @Override
    public SignInResponse handleRequest(SignInRequest request, Context context) {
        SignInServiceImpl service = new SignInServiceImpl();
        return service.postSignIn(request);
    }
}
