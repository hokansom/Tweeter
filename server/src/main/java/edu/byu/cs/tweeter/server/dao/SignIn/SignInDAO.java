package edu.byu.cs.tweeter.server.dao.SignIn;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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




//    static String hashPassword(String password){
//        /*TODO: DELETE THIS
//        String generatedPassword = null;
//        try{
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            //Add password bytes to digest
//            md.update(password.getBytes());
//            //Get the hash's bytes
//            byte[] bytes = md.digest();
//            //This bytes[] has bytes in decimal format;
//            //Convert it to hexadecimal format
//            StringBuilder sb = new StringBuilder();
//            for(int i=0; i< bytes.length ;i++)
//            {
//                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            //Get complete hashed password in hex format
//            generatedPassword = sb.toString();
//        } catch (NoSuchAlgorithmException e){
//            e.printStackTrace();
//        }
//        return generatedPassword;
//    }
}
