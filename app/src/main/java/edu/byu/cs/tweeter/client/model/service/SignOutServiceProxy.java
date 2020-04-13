package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.SignOutService;
import edu.byu.cs.tweeter.model.service.request.SignOutRequest;

public class SignOutServiceProxy implements SignOutService {

    private static final String URL_PATH = "/signout";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public void signOut(SignOutRequest request) throws IOException {
        serverFacade.signOut(request, URL_PATH);
    }
}
