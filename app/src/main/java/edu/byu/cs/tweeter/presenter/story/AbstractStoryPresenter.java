package edu.byu.cs.tweeter.presenter.story;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

abstract public class AbstractStoryPresenter extends Presenter {

    abstract public StoryResponse getStory(StoryRequest request);

    abstract public void updateNumStatuses(int num);
}
