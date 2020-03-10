package edu.byu.cs.tweeter.presenter.search;

import edu.byu.cs.tweeter.model.services.UserService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;

public class MockSearchPresenter extends AbstractSearchPresenter {
    private final SearchPresenter.View view;

    private final ServerFacade facade;
    @Override
    public SearchResponse searchAlias(SearchRequest request) {
        SearchResponse response = UserService.getTestingInstance(facade).searchUser(request);
        setViewingUser(response.getUser());
        setFollowing(response.isFollowing());
        return response;
    }

    public MockSearchPresenter(SearchPresenter.View view, ServerFacade facade) {
        this.view = view;
        this.facade = facade;
    }
}
