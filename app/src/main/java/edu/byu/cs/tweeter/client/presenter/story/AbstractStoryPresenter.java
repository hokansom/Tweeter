package edu.byu.cs.tweeter.client.presenter.story;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.client.presenter.Presenter;

abstract public class AbstractStoryPresenter extends Presenter {

    abstract public StoryResponse getStory(StoryRequest request) throws IOException;

    abstract public void updateNumStatuses(int num);
}
