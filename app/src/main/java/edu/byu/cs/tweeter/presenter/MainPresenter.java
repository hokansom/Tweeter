package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.SignInService;

public class MainPresenter extends Presenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void updateUserData();
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public void signOutUser(){
        SignInService.getInstance().setCurrentUser(null);
        view.updateUserData();
    }

    public MainPresenter(View view) {
        this.view = view;
    }
}
