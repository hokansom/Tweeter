package edu.byu.cs.tweeter.presenter.signIn;

import edu.byu.cs.tweeter.net.request.SignInRequest;
import edu.byu.cs.tweeter.net.response.SignInResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

public abstract class AbstractSignInPresenter extends Presenter {
    abstract public void updateHandle(String handle);

    abstract public void updatePassword(String password);

    abstract  public SignInResponse postSignIn(SignInRequest request);
}
