package edu.byu.cs.tweeter.model.services;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryServiceProxy implements StoryService {

    private static final String URL_PATH = "/getstory";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public StoryResponse getStory(StoryRequest request) throws IOException  {
        return serverFacade.getStory(request, URL_PATH);
    }
}
