package edu.byu.cs.tweeter.presenter.main;

import java.io.IOException;

import edu.byu.cs.tweeter.presenter.Presenter;

public abstract class AbstractMainPresenter extends Presenter {

    abstract public void signOut() throws IOException;

}
