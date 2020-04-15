package edu.byu.cs.tweeter.server.dao.signIn;

import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.server.service.HashService;

public interface SignInDAO {

    /**
     * Returns the user object and auth token for the given alias specified in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    public SignInResponse postSignIn(SignInRequest request);


    /**
     * Returns the hash of a password
     *
     * @param password
     * @return hashedPassword
     * */
    static String hashPassword(String password){
        String hashed;
        try{
            hashed = HashService.generatePasswordHash(password);
        } catch (Exception e){
            throw new RuntimeException("[Internal Service Error]: Could not hash password");
        }
        return hashed;
    }

    static boolean validatePassword(String original, String dbPassword){
        boolean matched = false;
        try{
            matched = HashService.validatePassword(original, dbPassword);

        } catch (Exception e) {
            throw new RuntimeException("[Internal Service Error]: Error occurred while validating password");
        }
        return matched;

    }
}
