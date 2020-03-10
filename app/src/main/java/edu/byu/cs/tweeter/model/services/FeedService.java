package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedService {

    private static FeedService instance;

    private final ServerFacade serverFacade;

    public static FeedService getInstance(){
        if(instance == null){
            instance = new FeedService();
        }

        return instance;
    }

    public static FeedService getTestingInstance(ServerFacade facade){
        if(instance == null){
            instance = new FeedService(facade);
        }
        return instance;
    }

    private FeedService(ServerFacade facade) { serverFacade = facade;}

    private FeedService(){ serverFacade = new ServerFacade();}

    public FeedResponse getFeed(FeedRequest request){
        return serverFacade.getFeed(request);
    }
}
