package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.follow.FollowDAO;
import edu.byu.cs.tweeter.server.dao.follow.FollowDAOImpl;
import edu.byu.cs.tweeter.server.dao.user.UserDAO;
import edu.byu.cs.tweeter.server.dao.user.UserDAOImpl;

public class DataFiller {
    private final static String DEFAULT_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final static int NUM_USERS = 10000;

    private final static User followed = new User("Test", "User", DEFAULT_IMAGE_URL );

    public static void fillDatabase() {

        UserDAO userDAO = new UserDAOImpl();
        FollowDAO followDAO = new FollowDAOImpl();

        List<User> followers = new ArrayList<>();
        List<User> users = new ArrayList<>();

        // Iterate over the number of users you will create
        for (int i = 1; i <= NUM_USERS; i++) {

            String name = "Person " + i;
            String alias = "Person" + i;
            String lastName = "Test";

            User user = new User();
            user.setAlias(alias);
            user.setFirstName(name);
            user.setLastName(lastName);
            user.setImageUrl(DEFAULT_IMAGE_URL);

            users.add(user);

            followers.add(user);
        }

        // Call the DAOs for the database logic
        if (users.size() > 0) {
            userDAO.addUserBatch(users);
        }
        if (followers.size() > 0) {
            followDAO.addFollowersBatch(followers, followed);
        }
    }
}
