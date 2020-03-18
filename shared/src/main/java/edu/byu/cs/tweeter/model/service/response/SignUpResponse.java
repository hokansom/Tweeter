package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SignUpResponse extends Response {

    public User user;


    public SignUpResponse(boolean success, String message) {
        super(success, message);
    }

    public SignUpResponse(User user){
        super(true);
        this.user = user;
    }

    public User getUser() { return user; }
}
