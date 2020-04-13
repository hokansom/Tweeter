package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.main.AbstractMainPresenter;
import edu.byu.cs.tweeter.client.presenter.main.MainPresenter;
import edu.byu.cs.tweeter.model.service.request.SignOutRequest;

public class PostSignOutTask extends AsyncTask<SignOutRequest, Void, Void> {
    private final AbstractMainPresenter presenter;
    private final SignOutObserver observer;

    private Exception exception;

    public interface SignOutObserver{
        void signOutRetrieved();
    }

    public PostSignOutTask(MainPresenter presenter, SignOutObserver observer){
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected Void doInBackground(SignOutRequest... signOutRequests) {
        try{
           presenter.signOut(signOutRequests[0]);
        }catch (IOException e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        observer.signOutRetrieved();
    }
}
