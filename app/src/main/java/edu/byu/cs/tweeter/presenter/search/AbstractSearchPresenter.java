package edu.byu.cs.tweeter.presenter.search;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

abstract public class AbstractSearchPresenter extends Presenter {
    abstract public SearchResponse getUser(SearchRequest request) throws IOException;

}
