package edu.byu.cs.tweeter.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SignInResponse extends Response {

    private User user;

    public SignInResponse(boolean success, String message){ super(success, message);}

    public SignInResponse(User user){
        super(true);
        this.user = user;
    }

    public User getUser() { return user; }
}
