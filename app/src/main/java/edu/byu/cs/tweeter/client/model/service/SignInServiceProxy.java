package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.SignInService;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;

public class SignInServiceProxy implements SignInService {

    private static final String URL_PATH = "/signin";

    private final ServerFacade serverFacade = new ServerFacade();

    private User currentUser;

    @Override
    public SignInResponse postSignIn(SignInRequest request) throws IOException {
        SignInResponse response = serverFacade.postSignIn(request, URL_PATH);
        setCurrentUser(response.getUser());
        return response;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
