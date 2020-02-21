package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;

public class FollowService {

    private static FollowService instance;

    private final ServerFacade serverFacade;

    public static FollowService getInstance(){
        if(instance == null){
            instance = new FollowService();
        }
        return instance;
    }

    private FollowService() { serverFacade = new ServerFacade();}

    public FollowResponse postFollow(FollowRequest request){
        return serverFacade.postFollow(request);
    }


}
