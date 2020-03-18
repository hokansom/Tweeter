package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedRequest {

    public final User request_user;
    public final int limit;
    public final Status last_status;

    public FeedRequest(User request_user, int limit, Status lastStatus){
        this.request_user = request_user;
        this.limit = limit;
        this.last_status = lastStatus;
    }

    public int getLimit() { return limit; }

    public User getUser() { return request_user; }

    public Status getLastStatus() { return last_status;}

}
