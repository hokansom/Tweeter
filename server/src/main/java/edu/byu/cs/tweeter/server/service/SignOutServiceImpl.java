package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.SignOutService;
import edu.byu.cs.tweeter.model.service.request.SignOutRequest;
import edu.byu.cs.tweeter.server.dao.signOut.SignOutDAOImpl;
import edu.byu.cs.tweeter.server.dao.user.UserDAOMock;

public class SignOutServiceImpl implements SignOutService {

    @Override
    public void signOut(SignOutRequest request){
        SignOutDAOImpl dao = new SignOutDAOImpl();
        dao.signOut(request);
    }
}
