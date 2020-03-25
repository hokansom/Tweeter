package edu.byu.cs.tweeter.client.presenter.follower;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.client.presenter.Presenter;

abstract public class AbstractFollowerPresenter extends Presenter {

    abstract public void updateNumFollowers(int num);

    abstract public FollowerResponse getFollowers(FollowerRequest request) throws IOException;
}
