package edu.byu.cs.tweeter.presenter.follower;

import edu.byu.cs.tweeter.model.services.FollowerService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

public class MockFollowerPresenter extends AbstractFollowerPresenter {

    private final ServerFacade facade;

    private final FollowerPresenter.View view;


    @Override
    public void updateNumFollowers(int num) {

    }

    @Override
    public FollowerResponse getFollowers(FollowerRequest request) {
        return FollowerService.getTestingInstance(facade).getFollowers(request);
    }

    public MockFollowerPresenter( FollowerPresenter.View view, ServerFacade facade) {
        this.facade = facade;
        this.view = view;
    }
}
