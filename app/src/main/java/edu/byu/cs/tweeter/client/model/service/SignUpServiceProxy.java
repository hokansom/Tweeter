package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SignUpService;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.client.net.ServerFacade;

public class SignUpServiceProxy implements SignUpService {

    private static final String URL_PATH = "/postsignup";


    private final ServerFacade serverFacade = new ServerFacade();

    @Override
    public SignUpResponse postSignUp(SignUpRequest request) throws IOException {
        return serverFacade.postSignUp(request, URL_PATH);
    }
}
