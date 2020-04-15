package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SignInResponse extends Response {

    public User user;
    public String token;

    public SignInResponse(boolean success, String message){ super(success, message);}

    public SignInResponse(User user, String token){
        super(true);
        this.user = user;
        this.token = token;
    }

    public User getUser() { return user; }

    public String getToken() { return token; }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
