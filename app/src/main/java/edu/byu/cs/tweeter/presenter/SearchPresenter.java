package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.UserService;
import edu.byu.cs.tweeter.net.request.SearchRequest;
import edu.byu.cs.tweeter.net.response.SearchResponse;

public class SearchPresenter extends Presenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public SearchPresenter(View view) { this.view = view; }

    public SearchResponse searchAlias(SearchRequest request){
        return UserService.getInstance().searchUser(request);
    }
}
