package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.SignInService;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class SignInServiceImpl implements SignInService {
    @Override
    public SignInResponse postSignIn(SignInRequest request) {
        UserDAO dao = new UserDAO();
        return dao.postSignIn(request);
    }
}
