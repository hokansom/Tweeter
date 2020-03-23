package edu.byu.cs.tweeter.presenter.search;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SearchUserService;
import edu.byu.cs.tweeter.model.services.SearchUserServiceProxy;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;

public class SearchPresenter extends AbstractSearchPresenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public SearchPresenter(View view) { this.view = view; }

    public SearchResponse getUser(SearchRequest request) throws IOException {
        SearchUserService service = new SearchUserServiceProxy();
        SearchResponse response = service.getUser(request);
        setViewingUser(response.getUser());
        setFollowing(response.isFollowing());
        return response;
    }
}
