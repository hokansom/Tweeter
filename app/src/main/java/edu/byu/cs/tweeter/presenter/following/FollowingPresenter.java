package edu.byu.cs.tweeter.presenter.following;


import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.services.FollowingServiceProxy;

public class FollowingPresenter extends AbstractFollowingPresenter {

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


    public FollowingResponse getFollowing(FollowingRequest request) throws IOException {
        FollowingService service = new FollowingServiceProxy();
        return service.getFollowees(request);
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
