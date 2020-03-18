package edu.byu.cs.tweeter.presenter.following;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

abstract public class AbstractFollowingPresenter extends Presenter {

    abstract public void updateNumFollowees(int num);

    abstract public FollowingResponse getFollowing(FollowingRequest request) throws IOException;
}
