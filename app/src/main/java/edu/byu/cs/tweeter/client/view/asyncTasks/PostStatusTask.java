package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.client.presenter.status.AbstractStatusPresenter;
import edu.byu.cs.tweeter.client.presenter.status.StatusPresenter;

public class PostStatusTask extends AsyncTask<StatusRequest, Void, StatusResponse> {
    private final AbstractStatusPresenter presenter;
    private final PostStatusObserver observer;

    private Exception exception;

    public interface PostStatusObserver{
        void statusRetrieved(StatusResponse response);
        void handleException(Exception ex);
    }

    @Override
    protected StatusResponse doInBackground(StatusRequest... statusRequests) {
        StatusResponse response = null;
        try{
            response = presenter.postStatus(statusRequests[0]);
        } catch (IOException e){
            exception = e;
        }
        return response; 
    }

    public PostStatusTask(StatusPresenter presenter, PostStatusObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected void onPostExecute(StatusResponse statusResponse){
        if(observer != null){
            if(exception == null){
                observer.statusRetrieved(statusResponse);
            } else {
                observer.handleException(exception);
            }
        }
    }

}
