package edu.byu.cs.tweeter.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SearchResponse extends Response {

    private User user;

    public SearchResponse(boolean success) {
        super(success);
    }

    public SearchResponse(boolean success, String message, User user) {
        super(success, message);
        this.user = user;
    }

    public User getUser() { return user; }
}
