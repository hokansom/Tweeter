package edu.byu.cs.tweeter.server.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;

/**
 * A DAO for accessing and updating 'user' data from the database.
 * SignUp DAO
 */
public class SignUpDAO {

    private static List<User> users;

    private static Map<String, String> authentication;

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
//            SignInRequest signInRequest = new SignInRequest(request.getUser().getAlias(), request.getPassword());
//            SignInDAO signInDAO = new SignInDAO();
//            SignInResponse signInResponse = signInDAO.postSignIn(signInRequest);
//            if(signInResponse.getUser() != null){
//                response = new SignUpResponse(signInResponse.getUser(), signInResponse.getToken());
//            }
//            else{
//                response = new SignUpResponse(false, signInResponse.getMessage());
//            }
            /**/
            response = new SignUpResponse(request.getUser(), generateAuthToken());
        }
        else{
            response = new SignUpResponse(false, "Bad Request: Alias is already taken");
        }

        return response;
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

    private String generateAuthToken(){
        return UUID.randomUUID().toString();
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
    UserGenerator getUserGenerator() { return UserGenerator.getInstance(); }
}
