package edu.byu.cs.tweeter.presenter.feed;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.model.services.FeedServiceProxy;
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

    public FeedResponse getFeed(FeedRequest request) throws IOException {
        FeedService service = new FeedServiceProxy();
        return service.getFeed(request);
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
