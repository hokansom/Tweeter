package edu.byu.cs.tweeter.server.dao.story;

import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public interface StoryDAO {
    /**
     * Gets the story from the database for the user specified in the request. Uses
     * information in the request object to limit the number of statuses returned and to return the
     * next set of statuses after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose story is to be returned and any
     *                other information required to satisfy the request.
     * @return the story (list of statuses).
     */

    StoryResponse getStory(StoryRequest request);

    StatusResponse postStatus(StatusRequest request);
}
