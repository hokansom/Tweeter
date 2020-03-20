package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.presenter.follower.AbstractFollowerPresenter;
import edu.byu.cs.tweeter.presenter.follower.FollowerPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetFollowerTask extends AsyncTask<FollowerRequest, Void, FollowerResponse> {
    private final AbstractFollowerPresenter presenter;
    private final GetFollowersObserver observer;

    private Exception exception;

    public interface GetFollowersObserver{
        void followersRetrieved(FollowerResponse followerResponse);
        void handleException(Exception ex);
    }

    public GetFollowerTask(FollowerPresenter presenter, GetFollowersObserver observer ){
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowerResponse doInBackground(FollowerRequest... followerRequests){
        FollowerResponse response = null;
        try{
            response = presenter.getFollowers(followerRequests[0]);
            loadImages(response);
        } catch (IOException e){
            exception = e;
        }

        return response;
    }

    private void loadImages(FollowerResponse response) {
        for(User user : response.getFollowers()) {

            Drawable drawable;

            try {
                drawable = ImageUtils.drawableFromUrl(user.getImageUrl());
            } catch (IOException e) {
                Log.e(this.getClass().getName(), e.toString(), e);
                drawable = null;
            }

            ImageCache.getInstance().cacheImage(user, drawable);
        }
    }

    @Override
    protected void onPostExecute(FollowerResponse followerResponse){
        if(observer != null){
            if(exception == null){
                observer.followersRetrieved(followerResponse);
            } else {
                observer.handleException(exception);
            }

        }
    }



}
