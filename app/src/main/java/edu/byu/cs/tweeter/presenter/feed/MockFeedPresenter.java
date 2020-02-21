package edu.byu.cs.tweeter.presenter.feed;

import edu.byu.cs.tweeter.model.services.FeedService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;

public class MockFeedPresenter extends AbstractFeedPresenter {

    private final FeedPresenter.View view;

    private final ServerFacade facade;

    public FeedResponse getFeed(FeedRequest request){
        return FeedService.getTestingInstance(facade).getFeed(request);
    }

    public MockFeedPresenter(FeedPresenter.View view, ServerFacade facade){
        this.view = view;
        this.facade = facade;
    }

    @Override
    public void updateNumStatuses(int num) {

    }
}
