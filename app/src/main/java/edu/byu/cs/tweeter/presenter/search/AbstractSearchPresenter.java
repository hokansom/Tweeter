package edu.byu.cs.tweeter.presenter.search;

import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

abstract public class AbstractSearchPresenter extends Presenter {
    abstract public SearchResponse searchAlias(SearchRequest request);

}
