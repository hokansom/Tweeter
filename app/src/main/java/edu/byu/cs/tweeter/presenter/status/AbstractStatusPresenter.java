package edu.byu.cs.tweeter.presenter.status;

import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

abstract public class AbstractStatusPresenter extends Presenter {

    abstract public void checkStatus(String s);
    abstract public StatusResponse postStatus(StatusRequest request);

}
