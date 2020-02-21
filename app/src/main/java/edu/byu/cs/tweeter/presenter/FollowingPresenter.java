package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.FollowingService;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.response.FollowingResponse;

public class FollowingPresenter extends Presenter {

    private final View view;

    private static int numberOfFollowees = 0;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void displayNoData(int visibility);
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public FollowingPresenter(View view) {
        this.view = view;
    }

    public FollowingResponse getFollowing(FollowingRequest request) {
        return FollowingService.getInstance().getFollowees(request);
    }

    public void updateNumFollowees(int num){
        numberOfFollowees = num;
        if(numberOfFollowees == 0){
            view.displayNoData(0);
        } else {
            view.displayNoData(8);
        }
    }
}
