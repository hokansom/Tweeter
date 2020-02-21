package edu.byu.cs.tweeter.presenter.follow;

import edu.byu.cs.tweeter.model.services.FollowService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;

public class MockFollowPresenter extends AbstractFollowPresenter {

    private final ServerFacade serverFacade;


    private final FollowPresenter.View view;

    @Override
    public FollowResponse postFollow(FollowRequest request) {
        return FollowService.getTestingInstance(serverFacade).postFollow(request);
    }

    @Override
    public boolean isFollowingRequest() {
        return false;
    }

    @Override
    public void updateFollowing() {

    }

    public MockFollowPresenter(FollowPresenter.View view, ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
        this.view = view;
    }
}
