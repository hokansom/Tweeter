package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class SignUpRequest {
    public User user;
    public String password;
    public String imageString;

    public User getUser() {
        return user;
    }

    public String getImageString() {
        return imageString;
    }

    public SignUpRequest() {}

    public SignUpRequest(User user, String password, String imageString){
        this.user = user;
        this.password = password;
        this.imageString = imageString;
    }

    public String getPassword() { return password; }

}
