package edu.byu.cs.tweeter.presenter.status;

import edu.byu.cs.tweeter.model.services.StatusService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.StatusResponse;

public class MockStatusPresenter extends AbstractStatusPresenter {
    private final StatusPresenter.View view;

    private final ServerFacade facade;

    @Override
    public void checkStatus(String s) {

    }

    @Override
    public StatusResponse postStatus(StatusRequest request) {
        return StatusService.getTestingInstance(facade).postStatus(request);
    }

    public MockStatusPresenter(StatusPresenter.View view, ServerFacade facade) {
        this.view = view;
        this.facade = facade;
    }
}
