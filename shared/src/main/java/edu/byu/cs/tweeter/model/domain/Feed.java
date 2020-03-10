package edu.byu.cs.tweeter.model.domain;

import java.util.List;

public class Feed {
    private List<Status> feed;

    private User user;

    public Feed(List<Status> feed, User user) {
        this.feed = feed;
        this.user = user;
    }

    public Feed(User user) {
        this.user = user;
    }

    public List<Status> getFeed() {
        return feed;
    }

    public void setFeed(List<Status> feed) {
        this.feed = feed;
    }

    public User getUser() {
        return user;
    }

}
