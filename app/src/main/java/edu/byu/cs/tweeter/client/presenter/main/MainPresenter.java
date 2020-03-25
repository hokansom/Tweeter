package edu.byu.cs.tweeter.client.presenter.main;


import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SignOutService;
import edu.byu.cs.tweeter.client.model.service.SignOutServiceProxy;

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

    public void signOut() throws IOException {
        SignOutService service = new SignOutServiceProxy();
        service.signOut();
        setCurrentUser(null);
        view.clearData();
    }

    public void signOutUser(){
        setCurrentUser(null);
        view.clearData();
    }


    public MainPresenter(View view) {
        this.view = view;
    }
}
