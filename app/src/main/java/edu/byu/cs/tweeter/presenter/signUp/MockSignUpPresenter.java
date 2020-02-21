package edu.byu.cs.tweeter.presenter.signUp;

import edu.byu.cs.tweeter.model.services.SignUpService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.SignUpResponse;

public class MockSignUpPresenter extends AbstractSignUpPresenter {
    private final SignUpPresenter.View view;

    private final ServerFacade facade;


    @Override
    public SignUpResponse postUser(SignUpRequest request) {
        return SignUpService.getTestingInstance(facade).postUser(request);
    }

    public MockSignUpPresenter(SignUpPresenter.View view, ServerFacade facade) {
        this.view = view;
        this.facade = facade;
    }
}
