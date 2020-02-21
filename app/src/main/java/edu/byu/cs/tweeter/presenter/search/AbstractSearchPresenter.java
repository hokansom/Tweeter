package edu.byu.cs.tweeter.presenter.search;

import edu.byu.cs.tweeter.net.request.SearchRequest;
import edu.byu.cs.tweeter.net.response.SearchResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

abstract public class AbstractSearchPresenter extends Presenter {
    abstract public SearchResponse searchAlias(SearchRequest request);

}
