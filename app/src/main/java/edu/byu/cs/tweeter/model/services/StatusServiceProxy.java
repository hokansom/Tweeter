package edu.byu.cs.tweeter.model.services;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusServiceProxy implements StatusService {

    private static final String URL_PATH = "/poststatus";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public StatusResponse postStatus(StatusRequest request) throws IOException {
        return serverFacade.postStatus(request, URL_PATH);
    }
}
