package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;

public class SignOutService {

    private static SignOutService instance;

    public static SignOutService getInstance(){
        if(instance == null){
            instance = new SignOutService();
        }
        return instance;
    }

    private SignOutService(){
        LoginService.getInstance().setCurrentUser(null);
    }
}
