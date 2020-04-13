package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class UnfollowServiceProxy implements UnfollowService {

    private static final String URL_PATH = "/follow";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public FollowResponse deleteFollow(FollowRequest request) throws IOException {
        String authToken = request.getToken();
        return serverFacade.deleteFollow(request, authToken, URL_PATH);
    }


}
