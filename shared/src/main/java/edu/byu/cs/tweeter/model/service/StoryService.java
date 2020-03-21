package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

/**
 * Defines the interface for the 'story' service.
 */
public interface StoryService {
    /**
     * Returns the statuses (or story) written by the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the story.
     */
    StoryResponse getStory(StoryRequest request) throws IOException;
}
