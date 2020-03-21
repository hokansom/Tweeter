package edu.byu.cs.tweeter.server.dao;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

/**
 * A DAO for updating 'follow' data in the database.
 */
public class FollowDAO {

    private static List<Follow> follows;
    private static Set<User> users;


    public FollowResponse postFollow(FollowRequest request) {
        if(follows == null){
            follows = initializeFollows();
        }

        Follow follow = request.follow;
        User follower = follow.getFollower();
        User followee = follow.getFollowee();

        /* Check to see if the person is trying to follow themselves*/
        if(follower.equals(followee)){
            return new FollowResponse(false, "Bad Request: User can't follow or unfollow themself");
        }

        /*Check to see if the followee and follower exist*/
        if(!users.contains(followee)){
            return new FollowResponse(false, "Bad Request: Followee doesn't exist");
        }
        if(!users.contains(follower)){
            return new FollowResponse(false, "Bad Request: Follower doesn't exist");
        }

        if(request.getIsFollow()){
            return addFollow(follow);
        } else {
            return removeFollow(follow);
        }

    }

    private FollowResponse addFollow(Follow follow) {
        /* Check to see if the follow relationship already exists */
        if(follows.contains(follow)){
            return new FollowResponse(false, "Bad Request: Follow relationship already exists");
        }

        follows.add(follow);
        return new FollowResponse(true, "Follow posted");
    }

    private FollowResponse removeFollow(Follow follow) {
        /* Verify that the follow relationship already exists */
        if(!follows.contains(follow)){
            return new FollowResponse(false, "Bad Request: Cannot delete a follow relationship that doesn't exist");
        }
        follows.remove(follow);
        return new FollowResponse(true, "Follow deleted");
    }

    /**
     * Generates the follows data.
     */
    private List<Follow> initializeFollows() {

        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(100,
                0, 50, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);

        Set<User> allUsers = new HashSet<>();

        for(Follow follow: follows){
            allUsers.add(follow.getFollowee());
            allUsers.add(follow.getFollower());
        }
        users = allUsers;

        return follows;
    }


    /**
     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }

}
