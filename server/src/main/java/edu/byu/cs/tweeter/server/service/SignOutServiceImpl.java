package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SignOutService;
import edu.byu.cs.tweeter.server.dao.SignOutDAO;

public class SignOutServiceImpl implements SignOutService {

    @Override
    public void signOut(){
        SignOutDAO dao = new SignOutDAO();
        dao.signOut();
    }
}
