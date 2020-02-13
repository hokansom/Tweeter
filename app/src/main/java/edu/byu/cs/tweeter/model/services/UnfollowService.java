package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.UnfollowResponse;

public class UnfollowService {

    private static UnfollowService instance;

    private final ServerFacade serverFacade;

    public static UnfollowService getInstance(){
        if(instance == null){
            instance = new UnfollowService();
        }
        return instance;
    }

    private UnfollowService() { serverFacade = new ServerFacade();}

    public UnfollowResponse deleteFollow(UnfollowRequest request){
        return serverFacade.deleteFollow(request);
    }
}
