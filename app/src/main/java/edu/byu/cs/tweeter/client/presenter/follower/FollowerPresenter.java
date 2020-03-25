package edu.byu.cs.tweeter.client.presenter.follower;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.client.model.service.FollowerServiceProxy;

public class FollowerPresenter extends AbstractFollowerPresenter {

    private final View view;

    private static int numberOfFollowers = 0;

    public interface View{
        void displayNoData(int visibility);
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public FollowerPresenter(View view){ this.view = view;}

    public FollowerResponse getFollowers(FollowerRequest request) throws IOException {
        FollowerService service = new FollowerServiceProxy();
        return service.getFollowers(request);
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