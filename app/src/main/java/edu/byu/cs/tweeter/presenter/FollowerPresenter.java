package edu.byu.cs.tweeter.presenter;

import android.view.View;

import edu.byu.cs.tweeter.model.services.FollowerService;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.response.FollowerResponse;

public class FollowerPresenter extends Presenter {

    private final View view;

    public interface View{
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public FollowerPresenter(View view){ this.view = view;}

    public FollowerResponse getFollowers(FollowerRequest request){
        return FollowerService.getInstance().getFollowers(request);
    }

}
