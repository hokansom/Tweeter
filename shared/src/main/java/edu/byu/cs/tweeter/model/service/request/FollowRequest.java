package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Follow;

public class FollowRequest {
    public Follow follow;
    public boolean isFollow;
    public String token;
    //If isFollow is true then it is a follow request
    //If isFollow is false then it is an unfollow request

    public FollowRequest () {}

    public FollowRequest(Follow follow, boolean isFollow){
        this.follow = follow;
        this.isFollow = isFollow;
        this.token = "";
    }

    public FollowRequest(Follow follow, boolean isFollow, String token){
        this.follow = follow;
        this.isFollow = isFollow;
        this.token = token;
    }

    public String getToken() { return token; }

    public void setToken(String token){ this.token = token; }

    public Follow getFollow(){ return follow; }

    public boolean getIsFollow(){ return isFollow;}

}
