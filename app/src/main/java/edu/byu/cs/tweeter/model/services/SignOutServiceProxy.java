package edu.byu.cs.tweeter.model.services;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SignOutService;
import edu.byu.cs.tweeter.net.ServerFacade;

public class SignOutServiceProxy implements SignOutService {

    private static final String URL_PATH = "/postsignout";

    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public void signOut() throws IOException {
        serverFacade.signOut(URL_PATH);
    }
}
