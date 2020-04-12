package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.AuthorizationService;
import edu.byu.cs.tweeter.server.dao.AuthorizationDAO;

public class AuthorizationServiceImpl implements AuthorizationService {
    @Override
    public boolean checkAuthorization(String alias, String token) {
        AuthorizationDAO dao = new AuthorizationDAO();
        return dao.checkAuthorization(alias, token);
    }

    @Override
    public void updateAuthorization(String alias, String token) {
        AuthorizationDAO dao = new AuthorizationDAO();
        dao.updateAuthorization(alias, token);
    }
}
