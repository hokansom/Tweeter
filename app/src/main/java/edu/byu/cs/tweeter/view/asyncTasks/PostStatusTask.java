package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.StatusPresenter;

public class PostStatusTask extends AsyncTask<StatusRequest, Void, StatusResponse> {
    private final StatusPresenter presenter;
    private final PostStatusObserver observer;

    public interface PostStatusObserver{
        void statusRetrieved(StatusResponse response);
    }

    @Override
    protected StatusResponse doInBackground(StatusRequest... statusRequests) {
        StatusResponse response = presenter.postStatus(statusRequests[0]);
        return response; 
    }

    public PostStatusTask(StatusPresenter presenter, PostStatusObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected void onPostExecute(StatusResponse statusResponse){
        if(observer != null){
            observer.statusRetrieved(statusResponse);
        }
    }

}
