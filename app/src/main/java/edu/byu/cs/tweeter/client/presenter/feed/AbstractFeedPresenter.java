package edu.byu.cs.tweeter.client.presenter.feed;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.client.presenter.Presenter;

public abstract class AbstractFeedPresenter extends Presenter {

    abstract public FeedResponse getFeed(FeedRequest request) throws IOException;

    abstract public void updateNumStatuses(int num);
}
