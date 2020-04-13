package edu.byu.cs.tweeter.server.dao.signUp;

import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.server.service.HashService;

public interface SignUpDAO {
    /**
     * Returns the new user object based on the information given in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    SignUpResponse postSignUp(SignUpRequest request);


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
}
