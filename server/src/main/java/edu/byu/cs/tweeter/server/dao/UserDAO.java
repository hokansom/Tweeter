package edu.byu.cs.tweeter.server.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;

/**
 * A DAO for accessing 'user' data from the database.
 * This is used for signing in, signing out, signing up, and searching for a user.
 */
public class UserDAO {

    private User currentUser;

    private List<User> users;

    private static Map<User, List<User>> followeesByFollower;

    private static Map<String, String> authentication;

    private static Map<String, String> authTokens;

    /*--------------------------------SIGN IN--------------------------------------------*/

    /**
     * Returns the user object and auth token for the given alias specified in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    public SignInResponse postSignIn(SignInRequest request){
        if(users == null){
            users = initializeUsers();
        }
        if(authentication == null){
            initializeAuthentication();
        }
        String message;
        if(!authentication.containsKey(request.getAlias())){
            message = String.format("[Bad Request]: User with given alias (%s) does not exist.", request.getAlias());
            return new SignInResponse(false, message);
        }
        String hashed = hashPassword(request.getPassword());
        if(hashed == null){
            message = "Internal Service Error: Error signing in.";
            return new SignInResponse(false, message);
        }
        if(!hashed.equals(authentication.get(request.getAlias()))){
            message = "[Bad Request]: Invalid alias or password";
            return new SignInResponse(false, message);
        }
        User user = searchUser(request.getAlias());
        if(user != null){
            setCurrentUser(user);
            String token = generateAuthToken();
            if(null == authTokens){
                initializeAuthTokens();
            }
            authTokens.put(user.getAlias(), token);
            return new SignInResponse(user, token);
        }
        else{
            message = String.format("[Bad Request]: User with given alias (%s) does not exist.", request.getAlias());
            return new SignInResponse(false, message);
        }
    }

    /*--------------------------------SIGN UP-------------------------------------------*/

    /**
     * Returns the new user object based on the information given in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    public SignUpResponse postSignUp(SignUpRequest request){
        SignUpResponse response;
        if(users == null){
            List<User> testUsers = initializeUsers();
            users = new ArrayList<>();
            users.addAll(testUsers);
        }
        if(checkValidAlias(request.getUser().getAlias())){
            /*Add new user to the existing list of users*/
            users.add(request.getUser());

            if(authentication == null){
                initializeAuthentication();
            }
            String hashed = hashPassword(request.getPassword());
            authentication.put(request.getUser().getAlias(), hashed);

            //TODO: save the image and update the imageurl

            /*Log the new user in*/
            SignInRequest signInRequest = new SignInRequest(request.getUser().getAlias(), request.getPassword());
            SignInResponse signInResponse = postSignIn(signInRequest);
            if(signInResponse.getUser() != null){
                response = new SignUpResponse(signInResponse.getUser(), signInResponse.getToken());
            }
            else{
                response = new SignUpResponse(false, signInResponse.getMessage());
            }
//            /**/
//            response = new SignUpResponse(request.getUser(), generateAuthToken());
        }
        else{
            response = new SignUpResponse(false, "[Bad Request]: Alias is already taken");
        }

        return response;
    }

    /*--------------------------------SIGN OUT--------------------------------------------*/

    /**
     * Logs out the current user
     *
     * Deactivates the current user's auth tokens.
     * Sets current user to null
     *
     * */
    public void signOut(){
        //Deactivate person's auth token
        if(currentUser != null){
            String alias = currentUser.getAlias();
            if(authTokens != null){
                authTokens.put(alias, null);
            }

        }
        setCurrentUser(null);

    }


    /*--------------------------------SEARCH USER--------------------------------------------*/

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


    public void setCurrentUser(User user){
        currentUser = user;
    }

    public User getCurrentUser(){
        return currentUser;
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

    private boolean checkValidAlias(String alias){
        User user = searchUser(alias);
        if(user == null){
            return true;
        }
        else{
            return false;
        }
    }

    private String generateAuthToken(){
        return UUID.randomUUID().toString();
    }

    /**
     * Initializes map for storing auth tokens
     * */
    private void initializeAuthTokens(){
        authTokens = getUserGenerator().generateTokens(users);
    }

    /**
     *
     * initializes user passwords
     */
    private void initializeAuthentication(){
        authentication = getUserGenerator().generatePasswords(users);
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

    /**
     * Returns the hash of a password
     *
     * @param password
     * @return hashedPassword
     * */
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


}
