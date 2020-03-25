package edu.byu.cs.tweeter.client.presenter.status;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.client.presenter.Presenter;

abstract public class AbstractStatusPresenter extends Presenter {

    abstract public void checkStatus(String s);
    abstract public StatusResponse postStatus(StatusRequest request) throws IOException;

}
