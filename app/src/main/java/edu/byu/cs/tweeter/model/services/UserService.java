package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.SearchRequest;
import edu.byu.cs.tweeter.net.response.SearchResponse;

public class UserService {

    private static UserService instance;

    private final ServerFacade serverFacade;


    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

    private UserService(){
        serverFacade = new ServerFacade();
    }

    public SearchResponse searchUser(SearchRequest request){
        return serverFacade.searchUser(request);
    }
}
