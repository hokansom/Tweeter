package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.SignInService;
import edu.byu.cs.tweeter.net.request.SearchRequest;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;

public abstract class Presenter {

    private static User viewingUser;

    public static boolean following;

    public User getCurrentUser() {
        return SignInService.getInstance().getCurrentUser();
    }

    public User getViewingUser() {
        if(viewingUser != null){
            return viewingUser;
        }
        return getCurrentUser();
    }

    public void setViewingUser(User user){
        viewingUser = user;
    }

    public boolean isFollowing(){ return following; }

    public void setFollowing(boolean following){
        following = following;
    }
}
