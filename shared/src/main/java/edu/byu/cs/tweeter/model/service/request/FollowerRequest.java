package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowerRequest {
    public final User followee;
    public final int limit;
    public final User lastFollower;

    public FollowerRequest(User following, int limit, User lastFollowing){
        this.followee = following;
        this.limit = limit;
        this.lastFollower = lastFollowing;
    }

    public User getFollowee() { return followee; }

    public int getLimit() { return limit; }

    public User getLastFollower() { return lastFollower; }
}
