package edu.byu.cs.tweeter.server.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

/**
 * A DAO for accessing 'follower' data from the database.
 */
public class FollowerDAO {
    private static Map<User, List<User>> followersByFollowee;

    /**
     * Gets the users from the database that are following the user specified in the request. Uses
     * information in the request object to limit the number of followers returned and to return the
     * next set of followers after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the followers.
     */

    public FollowerResponse getFollowers(FollowerRequest request) {
        assert request.getLimit() > 0;
        assert request.getFollowee() != null;

        if(followersByFollowee == null) {
            followersByFollowee = initializeFollowers();
        }
        List<User> allFollowers = followersByFollowee.get(request.getFollowee());
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowers != null) {
                int followersIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);

                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                    responseFollowers.add(allFollowers.get(followersIndex));
                }

                hasMorePages = followersIndex < allFollowers.size();
            }
            else{
                return new FollowerResponse(responseFollowers, false, 0);
            }
        }

        return new FollowerResponse(responseFollowers, hasMorePages, allFollowers.size());
    }

    /**
     * Determines the index for the first follower in the specified 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next follower after the
     * specified 'lastFollower'.
     *
     * @param lastFollower the last follower that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowers the generated list of followers from which we are returning paged results.
     * @return the index of the first follower to be returned.
     */
    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers) {

        int followersIndex = 0;

        if(lastFollower != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollower.equals(allFollowers.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                }
            }
        }

        return followersIndex;
    }

    /**
     * Generates the follower data.
     */
    private Map<User, List<User>> initializeFollowers() {
        Map<User, List<User>> followersByFollowee = new HashMap<>();

        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(50,
                0, 25, FollowGenerator.Sort.FOLLOWEE_FOLLOWER);

        // Populate a map of followers, keyed by followee so we can easily handle follower requests
        for(Follow follow : follows) {
            List<User> followers = followersByFollowee.get(follow.getFollowee());

            if(followers == null) {
                followers = new ArrayList<>();
                followersByFollowee.put(follow.getFollowee(), followers);
            }

            followers.add(follow.getFollower());
        }

        return followersByFollowee;
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
