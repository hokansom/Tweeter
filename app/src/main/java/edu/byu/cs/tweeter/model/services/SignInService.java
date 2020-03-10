package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;

public class SignInService {

    private static SignInService instance;

    private final ServerFacade serverFacade;

    private User currentUser;

    public static SignInService getInstance() {
        if(instance == null) {
            instance = new SignInService();
        }

        return instance;
    }

    public static SignInService getTestingInstance(ServerFacade facade){
        if(instance == null){
            instance = new SignInService(facade);
        }
        return instance;
    }


    private SignInService(){
        serverFacade = new ServerFacade();
    }

    public SignInService(ServerFacade facade){
        serverFacade = facade;
    }


    public SignInResponse postSignIn(SignInRequest request){
        SignInResponse response = serverFacade.postSignIn(request);
        setCurrentUser(response.getUser());
        return response;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
