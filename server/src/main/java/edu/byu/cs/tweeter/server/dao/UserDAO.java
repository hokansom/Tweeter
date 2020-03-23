package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;

/**
 * A DAO for accessing 'user' data from the database.
 */
public class UserDAO {
    private static Map<User, List<User>> followeesByFollower;
    private List<User> users;

    /**
     * Gets the user from the database that has the specified alias from the request.
     *
     * @param request contains information about the user whose is being retrieved.
     * @return the user.
     */
    public SearchResponse getUser(SearchRequest request){
        User user = searchUser(request.getAlias());
        boolean success = (user != null);
        String message;
        if(success){
            message = String.format("Found user with given alias %s", request.getAlias());
        }
        else{
            message = String.format("Could not find user with given alias %s", request.getAlias());
        }
        boolean isFollowing = searchFollow(request.getCurrentUser(), user);

        return new SearchResponse(success, message, user, isFollowing);
    }


    private User searchUser(String alias){
        if(users == null){
            users = new ArrayList<>();
            users.addAll(initializeUsers());
        }

        User desiredUser = null;
        for(User user: users) {
            if(user.getAlias().equals(alias)){
                desiredUser = user;
                break;
            }
        }
        return desiredUser;
    }

    /**
     * Initializes user data
     * */
    private List<User> initializeUsers(){
        List<User> testUsers = getUserGenerator().generateUsers(50);
        users = new ArrayList<>();
        users.addAll(testUsers);
        User testUser = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);
        users.add(testUser);

        return users;
    }

    /**
     * Returns an instance of UseGenerator that can be used to generate User data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    UserGenerator getUserGenerator() { return UserGenerator.getInstance(); }


    private boolean searchFollow(User follower, User followee){
        if(followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }
        List<User> followees = followeesByFollower.get(follower);
        if(followees == null){
            return false;
        } else {
            return followees.contains(followee);
        }
    }

    /**
     * Generates the followee data.
     */
    private Map<User, List<User>> initializeFollowees() {

        Map<User, List<User>> followeesByFollower = new HashMap<>();

        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(100,
                0, 50, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);

        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for(Follow follow : follows) {
            List<User> followees = followeesByFollower.get(follow.getFollower());

            if(followees == null) {
                followees = new ArrayList<>();
                followeesByFollower.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }

        return followeesByFollower;
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
