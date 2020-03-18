package edu.byu.cs.tweeter.model.services;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

public class FollowingServiceProxy implements FollowingService {

    private static final String URL_PATH = "/getfollowing";

//    private static FollowingService instance;

    private final ServerFacade serverFacade = new ServerFacade();

//    public static FollowingService getInstance() {
//        if(instance == null) {
//            instance = new FollowingService();
//        }
//
//        return instance;
//    }
//
//    public static FollowingService getTestingInstance(ServerFacade facade) {
//        if(instance == null) {
//            instance = new FollowingService(facade);
//        }
//
//        return instance;
//    }
//
//    private FollowingService(ServerFacade serverFacade) {
//        this.serverFacade = serverFacade;
//    }
//
//    private FollowingService() {
//        serverFacade = new ServerFacade();
//
    @Override
    public FollowingResponse getFollowees(FollowingRequest request) throws IOException {
        return serverFacade.getFollowees(request, URL_PATH);
    }
}
