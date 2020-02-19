package edu.byu.cs.tweeter.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowingResponse extends PagedResponse {

    private List<User> followees;
    private int numOffollowees;

    public FollowingResponse(String message) {
        super(false, message, false);
    }

    public FollowingResponse(List<User> followees, boolean hasMorePages, int numOffollowees) {
        super(true, hasMorePages);
        this.followees = followees;
        this.numOffollowees = numOffollowees;
    }


    public List<User> getFollowees() {
        return followees;
    }

    public int getNumOffollowees() {
        return numOffollowees;
    }
}
