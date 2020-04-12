package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedRequest {

    public  User request_user;
    public  int limit;
    public Status last_status;
    public String token;

    public FeedRequest () {}

    public FeedRequest(User request_user, int limit, Status lastStatus, String token){
        this.request_user = request_user;
        this.limit = limit;
        this.last_status = lastStatus;
        this.token = token;
    }

    public int getLimit() { return limit; }

    public User getUser() { return request_user; }

    public Status getLastStatus() { return last_status;}

    public String getToken() {
        return token;
    }
}
