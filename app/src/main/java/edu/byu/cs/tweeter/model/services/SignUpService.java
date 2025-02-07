package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.SignUpResponse;

public class SignUpService {
    private static SignUpService instance;

    private final ServerFacade serverFacade;

    public static SignUpService getInstance(){
        if(instance == null){
            instance = new SignUpService();
        }
        return instance;
    }



    public static SignUpService getTestingInstance(ServerFacade facade){
        if(instance == null){
            instance = new SignUpService(facade);
        }
        return instance;
    }

    private SignUpService(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    private SignUpService() { serverFacade = new ServerFacade(); }

    public SignUpResponse postUser(SignUpRequest request){
        return serverFacade.postUser(request);
    }
}
