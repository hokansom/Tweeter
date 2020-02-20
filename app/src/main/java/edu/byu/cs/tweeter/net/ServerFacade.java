package edu.byu.cs.tweeter.net;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.tweeter.model.domain.Feed;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.SignInService;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.SearchRequest;
import edu.byu.cs.tweeter.net.request.SignInRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.SearchResponse;
import edu.byu.cs.tweeter.net.response.SignInResponse;
import edu.byu.cs.tweeter.net.response.SignInResponse;
import edu.byu.cs.tweeter.net.response.SignUpResponse;
import edu.byu.cs.tweeter.net.response.StatusResponse;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.net.response.UnfollowResponse;

public class ServerFacade {

    private static Map<User, List<User>> followeesByFollower;

    private static Map<User, List<User>> followersByFollowee;

    private static Map<User, List<Status>> statusesByUser;

    private static Set<User> allUsers;

    private static Map<String, String> authentication;

    /*--------------------------------FOLLOWEE-----------------------------------------------------*/

    public FollowingResponse getFollowees(FollowingRequest request) {

        assert request.getLimit() >= 0;
        assert request.getFollower() != null;

        if(followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }

        List<User> allFollowees = followeesByFollower.get(request.getFollower());
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
            else{
                return new FollowingResponse(responseFollowees, false, 0);
            }
        }

        return new FollowingResponse(responseFollowees, hasMorePages, allFollowees.size());
    }

    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
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


    /*--------------------------------------FOLLOWER-----------------------------------------------------*/


    public FollowerResponse getFollowers(FollowerRequest request) {

        assert request.getLimit() >= 0;
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

    /* Generate follower data */
    private Map<User, List<User>> initializeFollowers() {
        Map<User, List<User>> followersByFollowee = new HashMap<>();
        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(100,
                0, 50, FollowGenerator.Sort.FOLLOWEE_FOLLOWER);

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


    /*------------------------------------------FOLLOW RELATED-------------------------------------*/


    public FollowResponse postFollow(FollowRequest request){
        if(allUsers == null){
            getAllUsers();
        }

        Follow follow = request.getFollow();
        User follower = follow.getFollower();
        User followee = follow.getFollowee();

        /* Check to see if the follower and followee actually exist */
        if(!allUsers.contains(followee)){
            return new FollowResponse(false, "Followee doesn't exist");
        }
        if(!allUsers.contains(follower)){
            return new FollowResponse(false, "Follower doesn't exist");
        }

        if(follower.equals(followee)){
            return new FollowResponse(false, "User can't follow themself");
        }

        List<User> followers = followersByFollowee.get(followee);

        if(followers == null){
            followers = new ArrayList<User>();
            followersByFollowee.put(followee, followers);
        }

        List<User> followees = followeesByFollower.get(follower);
        if(followees == null){
            followees = new ArrayList<User>();
            followeesByFollower.put(follower, followees);
        }

        if(followees.contains(followee)){
            return new FollowResponse(false, "Follow relationship already exists");
        }
        if(followers.contains(follower)){
            return new FollowResponse(false, "Follow relationship already exists");
        }

        followers.add(follower);
        followees.add(followee);

        return new FollowResponse(true, "Follow posted");
    }

    public UnfollowResponse deleteFollow(UnfollowRequest request){
        if(allUsers == null){
            getAllUsers();
        }

        Follow follow = request.getFollow();
        User follower = follow.getFollower();
        User followee = follow.getFollowee();

        /* Check to see if the follower and followee actually exist */
        if(!allUsers.contains(followee)){
            return new UnfollowResponse(false, "Followee doesn't exist");
        }
        if(!allUsers.contains(follower)){
            return new UnfollowResponse(false, "Follower doesn't exist");
        }


        List<User> followers = followersByFollowee.get(followee);


        List<User> followees = followeesByFollower.get(follower);

        if(followees == null || !followees.contains(followee)){
            return new UnfollowResponse(false, "Can't remove a follow relationship that doesn't exist.");
        }
        if(followers == null || !followers.contains(follower)){
            return new UnfollowResponse(false, "Can't remove a follow relationship that doesn't exist.");
        }

        followers.remove(follower);
        followees.remove(followee);

        return new UnfollowResponse(true, "Follow deleted");
    }



    /*------------------------------------------STATUS-------------------------------------*/


    private Map<User, List<Status>> initializeStatuses() {
        Map<User, List<Status>> statusesByUser = new HashMap<>();
        if(allUsers == null){
            getAllUsers();
        }

        List<Status> statuses = getStatusGenerator().generateAllStatuses(new ArrayList<>(allUsers), 0,5);
        for(Status status: statuses){
            List<Status> user_statuses = statusesByUser.get(status.getAuthor());
            if(user_statuses == null){
                user_statuses = new ArrayList<>();
                statusesByUser.put(status.getAuthor(), user_statuses);
            }
            user_statuses.add(status);
        }

        return statusesByUser;
    }

    StatusGenerator getStatusGenerator() {
        return StatusGenerator.getInstance();
    }

    private List<Status> sortStatuses(List<Status> statuses){
        Collections.sort(statuses, new Comparator<Status>() {
            @Override
            public int compare(Status status1, Status status2) {
                int result = status1.compareTo(status2);
                return result;
            }
        });
        return statuses;
    }

    private int getStatusStartingIndex(Status lastStatus, List<Status> allStatuses){
        int statusIndex = 0;

        if(lastStatus != null){
            for(int i = 0; i < allStatuses.size(); i++){
                if(lastStatus.equals(allStatuses.get(i))){
                    statusIndex = i + 1;
                    break;
                }
            }
        }

        return statusIndex;
    }

    private List<Status> getPagedStatuses(int limit, int statusIndex, List<Status> statuses){
        statuses = sortStatuses(statuses);
        List<Status> desiredStatuses = new ArrayList<>(limit);

        if(null != statuses ){
//            int statusIndex = getStatusStartingIndex(lastStatus, statuses);
            for(int limitCounter = 0; statusIndex < statuses.size() && limitCounter < limit; limitCounter++, statusIndex++){
                desiredStatuses.add(statuses.get(statusIndex));
            }

        }
        return desiredStatuses;
    }

    /*------------------------------------------STATUS Related------------------------------*/

    public StatusResponse postStatus(StatusRequest request){
        if(statusesByUser == null){
            statusesByUser = initializeStatuses();
        }
        List<Status> userStatuses = statusesByUser.get(request.getAuthor());
        if(userStatuses == null){
            userStatuses = new ArrayList<Status>();
        }
        userStatuses.add(request.getStatus());
        return new StatusResponse(true, "Status posted");
    }



    /*------------------------------------------FEED-------------------------------------*/

    public FeedResponse getFeed(FeedRequest request){
        List<Status> all_statuses = new ArrayList<>();
        List<Status> final_statuses = new ArrayList<>(request.getLimit());
        assert request.getLimit() >= 0;
        assert request.getUser() != null;
        int endingIndex = 0;

        if(statusesByUser == null){
            statusesByUser = initializeStatuses();
        }

        if(request.getLimit() > 0){
            List<User> allFollowees = followeesByFollower.get(request.getUser());
            if(allFollowees == null){
                return new FeedResponse(new Feed(new ArrayList<Status>(), request.getUser()), false);
            }
            for(User user: allFollowees){
                List<Status> temp_statuses = statusesByUser.get(user);
                if(null != temp_statuses){
                    for(Status status: temp_statuses){
                        all_statuses.add(status);
                    }
                }
            }
            int statusIndex = getStatusStartingIndex(request.getLastStatus(), all_statuses);
            final_statuses = getPagedStatuses(request.getLimit(), statusIndex, all_statuses);
            endingIndex  = statusIndex + final_statuses.size();

        }
        Feed feed = new Feed(final_statuses, request.getUser());
        boolean hasMorePages = endingIndex < all_statuses.size();

        return new FeedResponse(feed, hasMorePages);
    }




    /*------------------------------------------STORY-------------------------------------*/



    public StoryResponse getStory(StoryRequest request){
        List<Status> all_statuses = new ArrayList<>();
        List<Status> final_statuses = new ArrayList<>(request.getLimit());
        assert request.getLimit() >= 0;
        assert request.getUser() != null;

        int endingIndex = 0;

        if(statusesByUser == null){
            statusesByUser = initializeStatuses();
        }

        if(request.getLimit() > 0){
           all_statuses = statusesByUser.get(request.getUser());
           if(all_statuses == null){
               return new StoryResponse(new Story(new ArrayList<Status>(), request.getUser()), false);
           }
           int statusIndex = getStatusStartingIndex(request.getLastStatus(), all_statuses);
           final_statuses = getPagedStatuses(request.getLimit(), statusIndex, all_statuses);
           endingIndex = statusIndex + final_statuses.size();
        }

        Story story = new Story(final_statuses, request.getUser());

        boolean hasMorePages = endingIndex < all_statuses.size();

        return new StoryResponse(story, hasMorePages);
    }



    /*------------------------------------------USER-RELATED-------------------------------------*/

    private void getAllUsers(){
        if(followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }
        if(followersByFollowee == null){
            followersByFollowee = initializeFollowers();
        }
        Set<User> users1 = followeesByFollower.keySet();
        Set<User> users2 = followersByFollowee.keySet();

        Set<User> users = new HashSet<>();
        users.addAll(users1);
        users.addAll(users2);
        allUsers = users;

    }

    public SignInResponse postSignIn(SignInRequest request){
        if(authentication == null){
            initializeAuthentication();
        }
        String message;
        if(!authentication.containsKey(request.getAlias())){
            message = String.format("User with given alias (%s) does not exist.");
            return new SignInResponse(false, message);
        }
        String hashed = hashPassword(request.getPassword());
        if(hashed == null){
            message = "Error signing in.";
            return new SignInResponse(false, message);
        }
        if(!hashed.equals(authentication.get(request.getAlias()))){
            message = "Invalid alias or password";
            return new SignInResponse(false, message);
        }
        User user = searchUser(request.getAlias());
        if(user != null){
            return new SignInResponse(user);
        }
        else{
            message = String.format("User with given alias (%s) does not exist.");
            return new SignInResponse(false, message);
        }

    }

    public SignUpResponse postUser(SignUpRequest request){
        SignUpResponse response;
        if(checkValidAlias(request.getNewUser().getAlias())){
            /*Add new user to the existing list of users*/
            followeesByFollower.put(request.getNewUser(), new ArrayList<User>());
            followersByFollowee.put(request.getNewUser(), new ArrayList<User>());
            allUsers.add(request.getNewUser());

            /*Log the new user in*/
            SignInService.getInstance().setCurrentUser(request.getNewUser());
            response = new SignUpResponse(request.getNewUser());
        }
        else{
            response = new SignUpResponse(false, "Alias is already taken");
        }

        return response;
    }

    public SearchResponse searchUser(SearchRequest request ){
        User user = searchUser(request.getAlias());
        Boolean success = user != null ? true : false;
        String message;
        if(success){
            message = String.format("Found user with given alias %s", request.getAlias());
        }
        else{
            message = String.format("Could not find user with given alias %s", request.getAlias());
        }

        return new SearchResponse(success, message, user);
    }

    private User searchUser(String alias){
        if(allUsers == null){
            getAllUsers();
        }

        User desiredUser = null;
        for(User user: allUsers) {
            if(user.getAlias().equals(alias)){
                desiredUser = user;
                break;
            }
        }
        return desiredUser;
    }

    private boolean checkValidAlias(String alias){
        User user = searchUser(alias);
        if(user == null){
            return true;
        }
        else{
            return false;
        }
    }


    private String hashPassword(String password){
        String generatedPassword = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private void initializeAuthentication(){
        Map<String, String> auth = new HashMap<>();
        String testUser = "@TestUser";
        String hashedPass = hashPassword("Password");
        auth.put(testUser, hashedPass);
        authentication = auth;
    }
}

