package edu.byu.cs.tweeter.presenter.follower;

import android.view.View;

import edu.byu.cs.tweeter.model.services.FollowerService;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

public class FollowerPresenter extends AbstractFollowerPresenter {

    private final View view;

    private static int numberOfFollowers = 0;

    public interface View{
        void displayNoData(int visibility);
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public FollowerPresenter(View view){ this.view = view;}

    public FollowerResponse getFollowers(FollowerRequest request){
        return FollowerService.getInstance().getFollowers(request);
    }

    public void updateNumFollowers(int num){
        numberOfFollowers = num;
        if(numberOfFollowers == 0){
            view.displayNoData(0);
        } else {
            view.displayNoData(8);
        }
    }

}
