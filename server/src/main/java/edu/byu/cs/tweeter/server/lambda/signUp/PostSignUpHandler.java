package edu.byu.cs.tweeter.server.lambda.signUp;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.server.service.SignUpServiceImpl;

/**
 * An AWS lambda function that returns newly generated user.
 */
public class PostSignUpHandler implements RequestHandler<SignUpRequest, SignUpResponse> {
    /**
     * Returns the new user that was generated based off of the requets data.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the user.
     */
    @Override
    public SignUpResponse handleRequest(SignUpRequest request, Context context) {
        SignUpServiceImpl service = new SignUpServiceImpl();
        return service.postSignUp(request);
    }
}
