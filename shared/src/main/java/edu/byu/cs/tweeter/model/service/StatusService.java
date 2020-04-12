package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

/**
 * Defines the interface for the 'following' service.
 */
public interface StatusService {
    /**
     * Posts a new status to the database. Returns if the post succeeded or not
     *
     * @param request contains the data required to fulfill the request.
     * @return a success boolean
     *
     */
    StatusResponse postStatus(StatusRequest request) throws IOException;
}
