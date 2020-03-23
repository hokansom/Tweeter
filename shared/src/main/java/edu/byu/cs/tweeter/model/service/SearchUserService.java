package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;

/**
 * Defines the interface for the 'searchUser' service.
 */
public interface SearchUserService {

    /**
     * Returns the user with the given alias
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    SearchResponse getUser(SearchRequest request) throws IOException;
}
