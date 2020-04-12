package edu.byu.cs.tweeter.model.domain;

public class Authorization {
    private String alias;
    private String token;
    private long timeStamp;

    public Authorization(String alias, String token) {
        this.alias = alias;
        this.token = token;
        this.timeStamp = 0;
    }

    public Authorization(String alias, String token, long timeStamp) {
        this.alias = alias;
        this.token = token;
        this.timeStamp = timeStamp;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
