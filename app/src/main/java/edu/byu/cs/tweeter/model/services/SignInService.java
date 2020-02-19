package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;

public class SignInService {

    private static SignInService instance;

    private User currentUser;

    public static SignInService getInstance() {
        if(instance == null) {
            instance = new SignInService();
        }

        return instance;
    }

    private SignInService() {
        // TODO: Remove when the actual login functionality exists.
        currentUser = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        setCurrentUser(currentUser);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
