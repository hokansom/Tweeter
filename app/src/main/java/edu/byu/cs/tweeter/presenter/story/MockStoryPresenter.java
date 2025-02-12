package edu.byu.cs.tweeter.presenter.story;


import edu.byu.cs.tweeter.model.services.StoryService;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.response.StoryResponse;

public class MockStoryPresenter extends AbstractStoryPresenter {
    private final ServerFacade facade;

    private final StoryPresenter.View view;

    @Override
    public StoryResponse getStory(StoryRequest request) {
        return StoryService.getTestingInstance(facade).getStory(request);

    }

    @Override
    public void updateNumStatuses(int num) {
    }

    public MockStoryPresenter(StoryPresenter.View view, ServerFacade facade) {
        this.view = view;
        this.facade = facade;

    }
}
