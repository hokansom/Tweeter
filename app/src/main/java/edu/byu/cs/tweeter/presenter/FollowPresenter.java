package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.FollowService;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;

public class FollowPresenter extends Presenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void updateFollowButton(String text);
    }

    public FollowPresenter(View view){ this.view = view; }

    public FollowResponse postFollow(FollowRequest request){
        return FollowService.getInstance().postFollow(request);
    }

    public boolean isFollowingRequest(){ return !following;  }


    public void updateFollowing(){
        following = !following;
        if(following){
            view.updateFollowButton("Unfollow");
        } else {
            view.updateFollowButton("Follow");
        }
    }
}
