package edu.byu.cs.tweeter.model.service;


import java.io.IOException;

/**
 * Defines the interface for the 'sign out' service.
 */
public interface SignOutService {

    /**
    * Signs out the current user
    * */
    void signOut() throws IOException;
}