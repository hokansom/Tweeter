package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.UnfollowService;
import edu.byu.cs.tweeter.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.net.response.UnfollowResponse;

public class UnfollowPresenter extends Presenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public UnfollowPresenter(View view) { this.view = view;}

    public UnfollowResponse deleteFollow(UnfollowRequest request){
        return UnfollowService.getInstance().deleteFollow(request);
    }
}
