package edu.byu.cs.tweeter.presenter.feed;

import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

public abstract class AbstractFeedPresenter extends Presenter {

    abstract public FeedResponse getFeed(FeedRequest request);

    abstract public void updateNumStatuses(int num);
}
