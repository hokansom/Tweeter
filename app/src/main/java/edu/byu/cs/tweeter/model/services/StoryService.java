package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryService {

    private static StoryService instance;

    private final ServerFacade serverFacade;

    public static StoryService getInstance(){
        if(instance == null){
            instance = new StoryService();
        }

        return instance;
    }

    public static StoryService getTestingInstance(ServerFacade facade){
        if(instance == null){
            instance = new StoryService(facade);
        }

        return instance;
    }

    private StoryService(ServerFacade facade){ serverFacade = facade;}

    private StoryService(){ serverFacade = new ServerFacade();}

    public StoryResponse getStory(StoryRequest request){
        return serverFacade.getStory(request);
    }
}
