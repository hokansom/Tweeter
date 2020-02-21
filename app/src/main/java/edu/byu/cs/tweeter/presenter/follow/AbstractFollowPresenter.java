package edu.byu.cs.tweeter.presenter.follow;

import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

abstract public class AbstractFollowPresenter extends Presenter {

    abstract  public FollowResponse postFollow(FollowRequest request);

    abstract public boolean isFollowingRequest();

    abstract public void updateFollowing();
}
