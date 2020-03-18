package edu.byu.cs.tweeter.model.service.request;

public class SignInRequest {
    public final String alias;

    public final String password;

    public SignInRequest(String alias, String password) {
        this.alias = alias;
        this.password = password;
    }

    public String getAlias() { return alias; }

    public String getPassword() { return password; }
}
