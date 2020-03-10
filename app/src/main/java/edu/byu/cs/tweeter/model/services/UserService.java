package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;

public class UserService {

    private static UserService instance;

    private final ServerFacade serverFacade;


    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

    public static UserService getTestingInstance(ServerFacade facade){
        if(instance == null){
            instance = new UserService(facade);
        }
        return instance;
    }

    public UserService(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    private UserService(){
        serverFacade = new ServerFacade();
    }

    public SearchResponse searchUser(SearchRequest request){
        return serverFacade.searchUser(request);
    }
}
