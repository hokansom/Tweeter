package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class SignUpRequest {
    private final User user;
    private final String password;

    public SignUpRequest(User user, String password){
        this.user = user;
        this.password = password;
    }

    public User getNewUser() { return user; }

    public String getPassword() { return password; }

}
