package edu.byu.cs.tweeter.model.service.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowerResponse extends PagedResponse {
    public List<User> followers;
    public FollowerResponse(String message){
        super(false, message,false);
    }

    public FollowerResponse(List<User> followers, boolean hasMorePages){
        super(true, hasMorePages);
        this.followers = followers;
    }

    public List<User> getFollowers(){
        return this.followers;
    }

}
