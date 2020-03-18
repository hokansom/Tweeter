package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class SignUpRequest {
    public final User user;
    public final String password;
    public final String imageString;

    public SignUpRequest(User user, String password, String imageString){
        this.user = user;
        this.password = password;
        this.imageString = imageString;
    }

    public User getNewUser() { return user; }

    public String getPassword() { return password; }

}
