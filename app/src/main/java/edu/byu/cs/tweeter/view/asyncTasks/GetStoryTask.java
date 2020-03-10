package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.presenter.story.AbstractStoryPresenter;
import edu.byu.cs.tweeter.presenter.story.StoryPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetStoryTask extends AsyncTask<StoryRequest, Void, StoryResponse> {

    private final AbstractStoryPresenter presenter;
    private final GetStoryObserver observer;

    public interface GetStoryObserver{
        void storyRetrieved(StoryResponse StoryResponse);
    }

    public GetStoryTask(StoryPresenter presenter, GetStoryObserver observer){
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected StoryResponse doInBackground(StoryRequest... storyRequests){
        StoryResponse response = presenter.getStory(storyRequests[0]);
        loadImages(response);
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
            observer.storyRetrieved(StoryResponse);
        }
    }
}
