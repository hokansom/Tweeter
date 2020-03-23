package edu.byu.cs.tweeter.model.service.request;

public class SignInRequest {
    public String alias;

    public String password;

    public SignInRequest() {}

    public SignInRequest(String alias, String password) {
        this.alias = alias;
        this.password = password;
    }

    public String getAlias() { return alias; }

    public String getPassword() { return password; }
}
