package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.response.FollowingResponse;

public class FollowingService {

    private static FollowingService instance;

    private final ServerFacade serverFacade;

    public static FollowingService getInstance() {
        if(instance == null) {
            instance = new FollowingService();
        }

        return instance;
    }

    public static FollowingService getTestingInstance(ServerFacade facade) {
        if(instance == null) {
            instance = new FollowingService(facade);
        }

        return instance;
    }

    private FollowingService(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    private FollowingService() {
        serverFacade = new ServerFacade();
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        return serverFacade.getFollowees(request);
    }
}
