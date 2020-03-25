package edu.byu.cs.tweeter.client.presenter.story;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.StoryServiceProxy;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryPresenter extends AbstractStoryPresenter {

    private final View view;

    private static int numberOfstatuses = 0;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void displayNoData(int visible);
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public StoryPresenter(View view) { this.view = view; }

    public StoryResponse getStory(StoryRequest request) throws IOException {
        StoryService service = new StoryServiceProxy();
        return service.getStory(request);
    }

    public void updateNumStatuses(int num){
        numberOfstatuses = num;
        if(numberOfstatuses == 0){
            view.displayNoData(0);
        } else {
            view.displayNoData(8);
        }
    }
}
