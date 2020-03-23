package edu.byu.cs.tweeter.server.dao;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;

/**
 * A DAO for accessing 'user' data from the database.
 */
public class SignInDAO {

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
    public SignInResponse postSignIn(SignInRequest request){
        if(users == null){
            users = initializeUsers();
        }
        if(authentication == null){
            initializeAuthentication();
        }
        String message;
        if(!authentication.containsKey(request.getAlias())){
            message = String.format("Bad Request: User with given alias (%s) does not exist.", request.getAlias());
            return new SignInResponse(false, message);
        }
        String hashed = hashPassword(request.getPassword());
        if(hashed == null){
            message = "Internal Service Error: Error signing in.";
            return new SignInResponse(false, message);
        }
        if(!hashed.equals(authentication.get(request.getAlias()))){
            message = "Bad Request: Invalid alias or password";
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
            message = String.format("Bad Request: User with given alias (%s) does not exist.", request.getAlias());
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

    private void initializeAuthTokens(){
        authTokens = getUserGenerator().generateTokens(users);
    }

    private void initializeAuthentication(){
        authentication = getUserGenerator().generatePasswords(users);
    }


    /**
     * Initializes user data
     * */
    private List<User> initializeUsers(){
        List<User> users = getUserGenerator().generateUsers(50);
        /** FIXME:
        User testUser = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);
        users.add(testUser);
         **/

        return users;
    }

    /**
     * Returns an instance of UseGenerator that can be used to generate User data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    UserGenerator getUserGenerator() { return UserGenerator.getInstance(); }
}
