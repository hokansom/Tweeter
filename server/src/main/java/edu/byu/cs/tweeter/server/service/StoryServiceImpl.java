package edu.byu.cs.tweeter.server.service;


import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.story.StoryDAO;
import edu.byu.cs.tweeter.server.dao.story.StoryDAOImpl;
import edu.byu.cs.tweeter.server.dao.story.StoryDAOMock;

/**
 * Contains the business logic for getting the story of a user.
 */
public class StoryServiceImpl implements StoryService {
    @Override
    public StoryResponse getStory(StoryRequest request) {
        StoryDAO dao = new StoryDAOImpl();
        return dao.getStory(request);
    }
}
