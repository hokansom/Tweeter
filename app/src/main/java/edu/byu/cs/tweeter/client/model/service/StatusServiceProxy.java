package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusServiceProxy implements StatusService {

    private static final String URL_PATH = "/status";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public StatusResponse postStatus(StatusRequest request) throws IOException {
        String authToken = request.getToken();
        return serverFacade.postStatus(request, authToken, URL_PATH);
    }
}
