package edu.byu.cs.tweeter.client.presenter.signIn;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.client.presenter.Presenter;

public abstract class AbstractSignInPresenter extends Presenter {
    abstract public void updateHandle(String handle);

    abstract public void updatePassword(String password);

    abstract  public SignInResponse postSignIn(SignInRequest request) throws IOException;
}
