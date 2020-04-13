package edu.byu.cs.tweeter.model.service;


import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SignOutRequest;

/**
 * Defines the interface for the 'sign out' service.
 */
public interface SignOutService {

    /**
    * Signs out the current user
    * */
    void signOut(SignOutRequest request) throws IOException;
}
