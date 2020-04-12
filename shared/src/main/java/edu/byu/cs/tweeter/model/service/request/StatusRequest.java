package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusRequest {
    public User author;
    public Status status;
    public String token;

    public StatusRequest() {}

    public StatusRequest(User author, Status status){
        this.author = author;
        this.status = status;
        this.token="123456789";
    }

    public StatusRequest(User author, Status status, String token){
        this.author = author;
        this.status = status;
        this.token = token;
    }

    public User getAuthor() { return author; }

    public Status getStatus() { return status; }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
