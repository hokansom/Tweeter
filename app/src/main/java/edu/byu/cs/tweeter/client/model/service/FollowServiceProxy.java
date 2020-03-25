package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowServiceProxy implements FollowService {

    private static final String URL_PATH = "/follow";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public FollowResponse postFollow(FollowRequest request) throws IOException {
        return serverFacade.postFollow(request, URL_PATH);
    }


}
