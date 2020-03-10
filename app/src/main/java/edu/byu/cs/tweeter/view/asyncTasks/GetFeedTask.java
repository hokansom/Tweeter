package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.presenter.feed.AbstractFeedPresenter;
import edu.byu.cs.tweeter.presenter.feed.FeedPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetFeedTask extends AsyncTask<FeedRequest, Void, FeedResponse> {

    private final AbstractFeedPresenter presenter;
    private final GetFeedObserver observer;

    public interface GetFeedObserver{
        void feedRetrieved(FeedResponse feedResponse);
    }

    public GetFeedTask(FeedPresenter presenter, GetFeedObserver observer){
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FeedResponse doInBackground(FeedRequest... feedRequests){
        FeedResponse response = presenter.getFeed(feedRequests[0]);
        loadImages(response);
        return response;
    }

    private void loadImages(FeedResponse response) {
        for(edu.byu.cs.tweeter.model.domain.Status status : response.getFeed().getFeed()) {

            Drawable drawable;

            try {
                drawable = ImageUtils.drawableFromUrl(status.getAuthor().getImageUrl());
            } catch (IOException e) {
                Log.e(this.getClass().getName(), e.toString(), e);
                drawable = null;
            }

            ImageCache.getInstance().cacheImage(status.getAuthor(), drawable);
        }
    }

    @Override
    protected void onPostExecute(FeedResponse feedResponse){
        if(observer != null){
            observer.feedRetrieved(feedResponse);
        }
    }
}
