package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SignUpResponse extends Response {

    public User user;

    public String token;

    public SignUpResponse(boolean success, String message) {
        super(success, message);
    }

    public SignUpResponse(User user, String token){
        super(true);
        this.user = user;
        this.token = token;
    }

    public User getUser() { return user; }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
