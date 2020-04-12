package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.User;

public abstract class Presenter {

    private static User currentUser;

    private static String token;

    private static User viewingUser;

    public static boolean following = false;


    public void setCurrentUser(User user){
        currentUser = user;
    }


    public User getCurrentUser() {
        return currentUser;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        Presenter.token = token;
    }

    public boolean isFollowing(){ return following; }

    public void setFollowing(boolean following){
        following = following;
    }
}
