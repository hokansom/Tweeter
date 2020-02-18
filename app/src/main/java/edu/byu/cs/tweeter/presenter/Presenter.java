package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.SignInService;

public abstract class Presenter {

    public User getCurrentUser() {
        return SignInService.getInstance().getCurrentUser();
    }
}
