package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SignInService;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.server.dao.SignInDAO;

public class SignInServiceImpl implements SignInService {
    @Override
    public SignInResponse postSignIn(SignInRequest request) {
        SignInDAO dao = new SignInDAO();
        return dao.postSignIn(request);
    }
}
