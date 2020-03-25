package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.client.presenter.following.AbstractFollowingPresenter;
import edu.byu.cs.tweeter.client.presenter.following.FollowingPresenter;
import edu.byu.cs.tweeter.client.view.cache.ImageCache;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;

public class GetFollowingTask extends AsyncTask<FollowingRequest, Void, FollowingResponse> {

    private final AbstractFollowingPresenter presenter;
    private final GetFolloweesObserver observer;

    private Exception exception;

    public interface GetFolloweesObserver {
        void followeesRetrieved(FollowingResponse followingResponse);
        void handleException(Exception ex);
    }

    public GetFollowingTask(FollowingPresenter presenter, GetFolloweesObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowingResponse doInBackground(FollowingRequest... followingRequests) {
        FollowingResponse response = null;
        try{
            response = presenter.getFollowing(followingRequests[0]);
            loadImages(response);
        } catch (IOException e){
            exception = e;
        }

        return response;
    }

    private void loadImages(FollowingResponse response) {
        for(User user : response.getFollowees()) {

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
    protected void onPostExecute(FollowingResponse followingResponse) {

        if(observer != null) {
            if(exception == null){
                observer.followeesRetrieved(followingResponse);
            } else {
                observer.handleException(exception);
            }

        }
    }
}
