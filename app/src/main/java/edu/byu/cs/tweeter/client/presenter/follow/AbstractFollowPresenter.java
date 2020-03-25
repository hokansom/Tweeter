package edu.byu.cs.tweeter.client.presenter.follow;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.client.presenter.Presenter;

abstract public class AbstractFollowPresenter extends Presenter {

    abstract  public FollowResponse postFollow(FollowRequest request) throws IOException;

    abstract public boolean isFollowingRequest();

    abstract public void updateFollowing();
}
