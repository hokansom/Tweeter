package edu.byu.cs.tweeter.model.services;

public class SignOutService {

    private static SignOutService instance;

    public static SignOutService getInstance(){
        if(instance == null){
            instance = new SignOutService();
        }
        return instance;
    }

//    private SignOutService(){
//        SignInServiceProxy.getInstance().setCurrentUser(null);
//    }
}
