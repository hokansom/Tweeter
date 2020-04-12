package edu.byu.cs.tweeter.model.service;

public interface AuthorizationService {
    boolean checkAuthorization(String alias, String token);
    void updateAuthorization(String alias, String token);
    String getAuthorization(String alias);
}
