package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Follow;

public class FollowRequest {
    public Follow follow;
    public boolean isFollow;
    //If isFollow is true then it is a follow request
    //If isFollow is false then it is an unfollow request

    public FollowRequest () {}

    public FollowRequest(Follow follow, boolean isFollow){
        this.follow = follow;
        this.isFollow = isFollow;
    }

    public Follow getFollow(){ return follow; }

    public boolean getIsFollow(){ return isFollow;}

}
