package edu.byu.cs.tweeter.presenter.feed;

import edu.byu.cs.tweeter.model.services.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedPresenter extends AbstractFeedPresenter {
    private final View view;

    private static int numberOfstatuses = 0;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void displayNoData(int visible);
    }

    public FeedPresenter(View view){ this.view = view; }

    public FeedResponse getFeed(FeedRequest request){
        return FeedService.getInstance().getFeed(request);
    }

    public void updateNumStatuses(int num){
        numberOfstatuses = num;
        if(numberOfstatuses == 0){
            view.displayNoData(0);
        } else {
            view.displayNoData(8);
        }
    }
}
