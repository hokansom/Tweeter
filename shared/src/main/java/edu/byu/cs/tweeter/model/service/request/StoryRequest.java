package edu.byu.cs.tweeter.model.service.request;


import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryRequest {

    public User request_user;
    public int limit;
    public Status last_status;

    public StoryRequest(){}

    public void setRequest_user(User request_user) {
        this.request_user = request_user;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLast_status(Status last_status) {
        this.last_status = last_status;
    }

    public StoryRequest(User request_user, int limit, Status last_status){
        this.request_user = request_user;
        this.limit = limit;
        this.last_status = last_status;
    }

    public int getLimit() { return limit; }

    public User getUser() { return request_user; }

    public Status getLastStatus() {
        return last_status;
    }

}
