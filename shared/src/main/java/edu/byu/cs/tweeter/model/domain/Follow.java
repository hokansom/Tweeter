package edu.byu.cs.tweeter.model.domain;

import java.util.Objects;

public class Follow {

    /* A follows B
    *   A is the follower
    *   B is the followee
    * */

    public User follower;
    public User followee;

    public Follow () {}

    public Follow(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowee() {
        return followee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow that = (Follow) o;
        return follower.equals(that.follower) &&
                followee.equals(that.followee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, followee);
    }

    @Override
    public String toString() {
        return "Follow{" +
                "follower=" + follower.getAlias() +
                ", followee=" + followee.getAlias() +
                '}';
    }
}
