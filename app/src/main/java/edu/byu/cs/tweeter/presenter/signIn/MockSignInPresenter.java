package edu.byu.cs.tweeter.presenter.signIn;

import edu.byu.cs.tweeter.model.services.SignInService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.SignInRequest;
import edu.byu.cs.tweeter.net.response.SignInResponse;

public class MockSignInPresenter extends AbstractSignInPresenter {

    private final SignInPresenter.View view;

    private final ServerFacade facade;

    @Override
    public void updateHandle(String handle) {

    }

    @Override
    public void updatePassword(String password) {

    }

    @Override
    public SignInResponse postSignIn(SignInRequest request) {
        return SignInService.getInstance().postSignIn(request);
    }

    public MockSignInPresenter(SignInPresenter.View view, ServerFacade facade){
        this.view = view;
        this.facade = facade;
    }
}
