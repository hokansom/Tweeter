package edu.byu.cs.tweeter.presenter.main;

import edu.byu.cs.tweeter.model.services.SignInService;

public class MainPresenter extends AbstractMainPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void updateUserData();
        void clearData();
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public void signOutUser(){
        SignInService.getInstance().setCurrentUser(null);
        view.clearData();
    }

    public void logOut(){
        SignInService.getInstance().setCurrentUser(null);
    }

    public MainPresenter(View view) {
        this.view = view;
    }
}
