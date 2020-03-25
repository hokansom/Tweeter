package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.SignOutService;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class SignOutServiceImpl implements SignOutService {

    @Override
    public void signOut(){
        UserDAO dao = new UserDAO();
        dao.signOut();
    }
}
