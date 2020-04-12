package edu.byu.cs.tweeter.server.dao.SignIn;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.server.dao.UserGenerator;

/**
 * A DAO for accessing 'user' data from the database.
 */
public class SignInDAOMock implements SignInDAO {

    private User currentUser;

    private static List<User> users;

    private static Map<String, String> authentication;

    private static Map<String, String> authTokens;

    /**
     * Returns the user object and auth token for the given alias specified in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    @Override
    public SignInResponse postSignIn(SignInRequest request){
        if(users == null){
            users = initializeUsers();
        }
        if(authentication == null){
            initializeAuthentication();
        }
        String message;
        if(!authentication.containsKey(request.getAlias())){
            message = String.format("[Bad Request]: User with given alias (@%s) does not exist.", request.getAlias());
            return new SignInResponse(false, message);
        }
        String hashed = SignInDAO.hashPassword(request.getPassword());
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


    private String generateAuthToken(){
        return UUID.randomUUID().toString();
    }


    private User searchUser(String alias){
        if(users == null){
            initializeUsers();
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


    private void setCurrentUser(User user){
        currentUser = user;
    }

    private User getCurrentUser(){
        return currentUser;
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
        List<User> users = getUserGenerator().generateUsers(50);

        return users;
    }

    /**
     * Returns an instance of UseGenerator that can be used to generate User data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    public UserGenerator getUserGenerator() { return UserGenerator.getInstance(); }
}
