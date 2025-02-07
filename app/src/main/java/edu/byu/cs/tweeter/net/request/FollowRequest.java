package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowRequest {
    private final Follow follow;
    private final boolean isFollow;
    //If isFollow is true then it is a follow request
    //If isFollow is false then it is an unfollow request

    public FollowRequest(Follow follow, boolean isFollow){
        this.follow = follow;
        this.isFollow = isFollow;
    }

    public Follow getFollow(){ return follow; }

    public boolean getIsFollow(){ return isFollow;}

}
