package edu.byu.cs.tweeter.model.service.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowerResponse extends PagedResponse {
    private List<User> followers;
    private int numOfFolllowers;

    public FollowerResponse(String message){
        super(false, message,false);
    }

    public FollowerResponse(List<User> followers, boolean hasMorePages, int numOfFolllowers){
        super(true, hasMorePages);
        this.followers = followers;
        this.numOfFolllowers = numOfFolllowers;
    }

    public List<User> getFollowers(){
        return this.followers;
    }

    public int getNumOfFolllowers() {
        return numOfFolllowers;
    }
}
