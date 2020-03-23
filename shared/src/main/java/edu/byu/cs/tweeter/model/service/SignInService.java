package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;

/**
 * Defines the interface for the 'sign in' service.
 */
public interface SignInService {
    /**
     * Returns the user object and auth token for the given alias specified in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    SignInResponse postSignIn(SignInRequest request) throws IOException;
}
