package edu.byu.cs.tweeter.presenter.feed;

import edu.byu.cs.tweeter.model.services.FeedServiceProxy;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

//public class MockFeedPresenter extends AbstractFeedPresenter {
//
//    private final FeedPresenter.View view;
//
//    private final ServerFacade facade;
//
//    public FeedResponse getFeed(FeedRequest request){
//        return FeedServiceProxy.getTestingInstance(facade).getFeed(request);
//    }
//
//    public MockFeedPresenter(FeedPresenter.View view, ServerFacade facade){
//        this.view = view;
//        this.facade = facade;
//    }
//
//    @Override
//    public void updateNumStatuses(int num) {
//
//    }
//}
