package edu.byu.cs.tweeter.presenter.signUp;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

abstract public class AbstractSignUpPresenter extends Presenter {

    abstract public SignUpResponse postSignUp(SignUpRequest request) throws IOException;

}
