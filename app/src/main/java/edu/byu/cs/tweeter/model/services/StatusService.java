package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusService {

    private static StatusService instance;

    private final ServerFacade serverFacade;

    public static StatusService getInstance(){
        if(instance == null){
            instance = new StatusService();
        }
        return instance;
    }

    public static StatusService getTestingInstance(ServerFacade facade){
        if(instance == null){
            instance = new StatusService(facade);
        }
        return instance;
    }

    private StatusService(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    private StatusService() { serverFacade = new ServerFacade();}

    public StatusResponse postStatus(StatusRequest request){
        return serverFacade.postStatus(request);
    }
}
