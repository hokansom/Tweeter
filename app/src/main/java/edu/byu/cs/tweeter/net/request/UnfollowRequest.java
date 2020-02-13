package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.Follow;

public class UnfollowRequest {
    private final Follow follow;

    public UnfollowRequest(Follow follow){
        this.follow = follow;
    }

    public Follow getFollow(){ return follow; }

}
