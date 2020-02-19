package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;

public class PostFollowTask extends AsyncTask<FollowRequest, Void, FollowResponse> {
    private final FollowPresenter presenter;
    private final PostFollowObserver observer;

    public interface PostFollowObserver{
        void followRetrieved(FollowResponse response);
    }

    @Override
    protected FollowResponse doInBackground(FollowRequest... followRequests) {
        FollowResponse response = presenter.postFollow(followRequests[0]);
        return response;
    }

    public PostFollowTask(FollowPresenter presenter, PostFollowObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected void onPostExecute(FollowResponse followResponse){
        if(observer != null){
            observer.followRetrieved(followResponse);
        }
    }

}
