package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusRequest {
    public User author;
    public Status status;

    public StatusRequest() {}

    public StatusRequest(User author, Status status){
        this.author = author;
        this.status = status;
    }

    public User getAuthor() { return author; }

    public Status getStatus() { return status; }

}
