package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.main.AbstractMainPresenter;
import edu.byu.cs.tweeter.client.presenter.main.MainPresenter;

public class PostSignOutTask extends AsyncTask<Void, Void, Void> {
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
    protected Void doInBackground(Void... voids) {
        try{
           presenter.signOut();
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
