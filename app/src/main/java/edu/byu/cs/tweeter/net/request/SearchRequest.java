package edu.byu.cs.tweeter.net.request;

public class SearchRequest {

    private final String alias;

    public SearchRequest(String alias){
        this.alias = alias;
    }

    public String getAlias() { return alias; }
}
