package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.SignInService;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.server.dao.signIn.SignInDAO;
import edu.byu.cs.tweeter.server.dao.signIn.SignInDAOImpl;

public class SignInServiceImpl implements SignInService {
    @Override
    public SignInResponse postSignIn(SignInRequest request) {
        SignInDAO dao = new SignInDAOImpl();
        SignInResponse response = dao.postSignIn(request);

        response.setToken(getAuthorization(request.getAlias()));
        return response;
    }

    private String getAuthorization(String alias){
        AuthorizationServiceImpl authorizationService = new AuthorizationServiceImpl();
        return authorizationService.getAuthorization(alias);
    }
}
