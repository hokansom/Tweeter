package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class SearchRequest {

    public final String alias;
    public final User currentUser;

    public SearchRequest(String alias, User currentUser){
        this.alias = alias;
        this.currentUser = currentUser;
    }

    public String getAlias() { return alias; }

    public User getCurrentUser() { return currentUser; }
}
