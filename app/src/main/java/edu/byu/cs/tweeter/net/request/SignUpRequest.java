package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class SignUpRequest {
    private final User user;

    public SignUpRequest(User user){
        this.user = user;
    }

    public User getNewUser() { return user; }

}
