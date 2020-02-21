package edu.byu.cs.tweeter.presenter.status;

import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

abstract public class AbstractStatusPresenter extends Presenter {

    abstract public void checkStatus(String s);
    abstract public StatusResponse postStatus(StatusRequest request);

}
