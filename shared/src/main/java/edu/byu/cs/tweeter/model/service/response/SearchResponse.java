package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SearchResponse extends Response {

    private User user;

    private boolean isFollowing;

    public SearchResponse(boolean success) {
        super(success);
    }

    public SearchResponse(boolean success, String message, User user, boolean isFollowing) {
        super(success, message);
        this.user = user;
        this.isFollowing = isFollowing;
    }

    public User getUser() { return user; }

    public boolean isFollowing() { return isFollowing; }
}
