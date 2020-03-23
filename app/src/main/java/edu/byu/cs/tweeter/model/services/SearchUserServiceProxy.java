package edu.byu.cs.tweeter.model.services;

import java.io.IOException;
import java.net.URL;

import edu.byu.cs.tweeter.model.service.SearchUserService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;

public class SearchUserServiceProxy implements SearchUserService {

    private static final String URL_PATH = "/getuser";

    private final ServerFacade serverFacade = new ServerFacade();

//    @Override
//    public SearchResponse getUser(SearchRequest request) throws IOException {
//        String completePath = URL_PATH + request.getAlias();
//        return serverFacade.getUser(completePath);
//    }

    @Override
    public SearchResponse getUser(SearchRequest request) throws IOException {
        return serverFacade.getUser(request, URL_PATH);
    }
}
