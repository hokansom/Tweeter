package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;

public class FollowerService {

    private static FollowerService instance;

    private final ServerFacade serverFacade;

    public static FollowerService getInstance() {
        if(instance == null) {
            instance = new FollowerService();
        }

        return instance;
    }

    public static FollowerService getTestingInstance(ServerFacade facade){
        if(instance == null){
            instance = new FollowerService(facade);
        }
        return instance;
    }

    public FollowerService(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    private FollowerService() {
        serverFacade = new ServerFacade();
    }

    public FollowerResponse getFollowers(FollowerRequest request) {
        return serverFacade.getFollowers(request);
    }
}
