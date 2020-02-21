package edu.byu.cs.tweeter.presenter.main;

import edu.byu.cs.tweeter.model.services.SignInService;
import edu.byu.cs.tweeter.net.ServerFacade;

public class MockMainPresenter extends AbstractMainPresenter {

    private final MainPresenter.View view;

    private final ServerFacade facade;

    @Override
    public void signOutUser() {
        SignInService.getTestingInstance(facade).setCurrentUser(null);
    }

    public MockMainPresenter(MainPresenter.View view, ServerFacade facade){
        this.view = view;
        this.facade = facade;
    }
}
