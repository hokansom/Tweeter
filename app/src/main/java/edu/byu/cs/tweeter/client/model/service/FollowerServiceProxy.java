package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

public class FollowerServiceProxy implements FollowerService {

    private static final String URL_PATH = "/followers";

    private final ServerFacade serverFacade = new ServerFacade();


    public FollowerResponse getFollowers(FollowerRequest request) throws IOException {
        return serverFacade.getFollowers(request, URL_PATH);
    }
}
