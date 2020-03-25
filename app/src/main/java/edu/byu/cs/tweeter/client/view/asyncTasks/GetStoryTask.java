package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.client.presenter.story.AbstractStoryPresenter;
import edu.byu.cs.tweeter.client.presenter.story.StoryPresenter;
import edu.byu.cs.tweeter.client.view.cache.ImageCache;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;

public class GetStoryTask extends AsyncTask<StoryRequest, Void, StoryResponse> {

    private final AbstractStoryPresenter presenter;
    private final GetStoryObserver observer;

    private Exception exception;

    public interface GetStoryObserver{
        void storyRetrieved(StoryResponse StoryResponse);
        void handleException(Exception ex);
    }

    public GetStoryTask(StoryPresenter presenter, GetStoryObserver observer){
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected StoryResponse doInBackground(StoryRequest... storyRequests){
        StoryResponse response = null;
        try{
            response = presenter.getStory(storyRequests[0]);
            if(response.success){
                loadImages(response);
            }
        } catch (IOException e){
            exception = e;
        }

        return response;
    }

    private void loadImages(StoryResponse response) {
        for(edu.byu.cs.tweeter.model.domain.Status status : response.getStory().getStory()) {

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
    protected void onPostExecute(StoryResponse StoryResponse){
        if(observer != null){
            if(exception == null){
                observer.storyRetrieved(StoryResponse);
            } else {
                observer.handleException(exception);
            }

        }
    }
}
