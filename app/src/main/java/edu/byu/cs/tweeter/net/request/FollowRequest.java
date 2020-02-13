package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowRequest {
    private final Follow follow;

    public FollowRequest(Follow follow){
        this.follow = follow;
    }

    public Follow getFollow(){ return follow; }

}
