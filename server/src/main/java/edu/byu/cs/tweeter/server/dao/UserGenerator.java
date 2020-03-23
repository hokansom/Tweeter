package edu.byu.cs.tweeter.server.dao;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * A temporary class that generates and returns {@link User} objects. This class may be removed when
 * the server is created and the ServerFacade no longer needs to return dummy data.
 */
public class UserGenerator {

    private static final String MALE_NAMES_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/mnames.json";
    private static final String FEMALE_NAMES_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/fnames.json";
    private static final String SURNAMES_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/snames.json";

    private static final String [] maleNames;
    private static final String [] femaleNames;
    private static final String [] surnames;

    static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private static UserGenerator instance;

    /**
     * A private constructor that ensures no instances of this class can be created.
     */
    private UserGenerator() {}

    /**
     * Returns the singleton instance of the class
     *
     * @return the instance.
     */
    public static UserGenerator getInstance() {
        if(instance == null) {
            instance = new UserGenerator();
        }

        return instance;
    }

    /*
     * Loads a lists of female first names, male first names, and surnames from the json files when
     * this class is loaded into memory.
     */
    static {
        try {
            maleNames = loadNamesFromJSon(MALE_NAMES_URL);
            femaleNames = loadNamesFromJSon(FEMALE_NAMES_URL);
            surnames = loadNamesFromJSon(SURNAMES_URL);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Loads and returns the names from the specified json file.
     *
     * @param urlString the url to the file containing the names.
     * @return the names.
     * @throws IOException if an IO error occurs.
     */
    private static String [] loadNamesFromJSon(String urlString) throws IOException {

        Names names;

        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                names = (new Gson()).fromJson(br, Names.class);

            } else {
                throw new IOException("Unable to read from url. Response code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } finally {
           if(connection != null) {
               connection.disconnect();
           }
        }

        return names == null ? null : names.getNames();
    }

    /**
     * Generates the specified number of users with names randomly selected from the json files.
     *
     * @param count the number of users to generate.
     * @return the generated users.
     */
    public List<User> generateUsers(int count) {

        List<User> users = new ArrayList<>(count);

        Random random = new Random();

        while(users.size() < count) {
            // Randomly determine if the user will be male or female and generate a gender
            // specific first name
            String firstName;
            String imageULR;
            if(random.nextInt(2) == 0) {
                firstName = maleNames[random.nextInt(maleNames.length)];
                imageULR = MALE_IMAGE_URL;
            } else {
                firstName = femaleNames[random.nextInt(femaleNames.length)];
                imageULR = FEMALE_IMAGE_URL;
            }

            String lastName = surnames[random.nextInt(surnames.length)];
            User user = new User(firstName, lastName, imageULR);

            if(!users.contains(user)) {
                users.add(user);
            }
        }

        return users;
    }

    public Map<String, String> generatePasswords(List<User> users){
        Map<String, String> auth = new HashMap<>();
        String testUser = "@TestUser";
        String hashedPass = hashPassword("Password");
        auth.put(testUser, hashedPass);
        return auth;
    }

    public Map<String, String> generateTokens(List<User> users){
        Map<String, String> tokens = new HashMap<>();
        return tokens;
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

    /**
     * A class used by Gson to map the json data to an instance of this class.
     */
    class Names {

        @SuppressWarnings("unused")
        private String [] data;

        private String [] getNames() {
            return data;
        }
    }
}
