package edu.byu.cs.tweeter.client.presenter.main;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.Presenter;
import edu.byu.cs.tweeter.model.service.request.SignOutRequest;

public abstract class AbstractMainPresenter extends Presenter {

    abstract public void signOut(SignOutRequest request) throws IOException;

}
