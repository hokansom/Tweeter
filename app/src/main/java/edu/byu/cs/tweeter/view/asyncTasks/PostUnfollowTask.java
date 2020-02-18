package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.presenter.UnfollowPresenter;

public class PostUnfollowTask extends AsyncTask<UnfollowRequest, Void, UnfollowResponse> {
    private final UnfollowPresenter presenter;
    private final PostUnfollowObserver observer;

    public interface PostUnfollowObserver{
        void unfollowRetrieved(UnfollowResponse response);
    }

    @Override
    protected UnfollowResponse doInBackground(UnfollowRequest... unfollowRequests) {
        UnfollowResponse response = presenter.deleteFollow(unfollowRequests[0]);
        return response; 
    }

    public PostUnfollowTask(UnfollowPresenter presenter, PostUnfollowObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected void onPostExecute(UnfollowResponse unfollowResponse){
        if(observer != null){
            observer.unfollowRetrieved(unfollowResponse);
        }
    }

}
