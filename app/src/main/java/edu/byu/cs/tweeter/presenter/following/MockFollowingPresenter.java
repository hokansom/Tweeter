package edu.byu.cs.tweeter.presenter.following;

import edu.byu.cs.tweeter.model.services.FollowingService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

public class MockFollowingPresenter extends AbstractFollowingPresenter {

    private final FollowingPresenter.View view;

    private ServerFacade facade;


    @Override
    public void updateNumFollowees(int num) {

    }

    @Override
    public FollowingResponse getFollowing(FollowingRequest request) {
        return FollowingService.getTestingInstance(facade).getFollowees(request);
    }

    public MockFollowingPresenter(FollowingPresenter.View view, ServerFacade facade) {
        this.view = view;
        this.facade = facade;
    }
}
