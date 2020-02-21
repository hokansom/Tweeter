package edu.byu.cs.tweeter.presenter.signUp;

import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.SignUpResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

abstract public class AbstractSignUpPresenter extends Presenter {

    abstract public SignUpResponse postUser(SignUpRequest request);

}
