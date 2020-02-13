package edu.byu.cs.tweeter.model.domain;

import java.util.List;

public class Story {

    private List<Status> story;
    private User user;

    public Story(List<Status> story, User user) {
        this.story = story;
        this.user = user;
    }

    public Story(User user) {
        this.user = user;
    }

    public List<Status> getStory() {
        return story;
    }

    public void setStory(List<Status> story) {
        this.story = story;
    }

    public User getUser() {
        return user;
    }
}
