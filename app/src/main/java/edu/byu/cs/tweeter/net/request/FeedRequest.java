package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedRequest {

    private final User request_user;
    private final int limit;
    private final Status last_status;

    public FeedRequest(User request_user, int limit, Status lastStatus){
        this.request_user = request_user;
        this.limit = limit;
        this.last_status = lastStatus;
    }

    public int getLimit() { return limit; }

    public User getUser() { return request_user; }

    public Status getLastStatus() { return last_status;}

}
