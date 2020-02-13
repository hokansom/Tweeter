package edu.byu.cs.tweeter.net.request;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowerRequest {
    private final User followee;
    private final int limit;
    private final User lastFollower;

    public FollowerRequest(User following, int limit, User lastFollowing){
        this.followee = following;
        this.limit = limit;
        this.lastFollower = lastFollowing;
    }

    public User getFollowee() { return followee; }

    public int getLimit() { return limit; }

    public User getLastFollower() { return lastFollower; }
}
