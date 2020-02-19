package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.SearchRequest;
import edu.byu.cs.tweeter.net.response.SearchResponse;
import edu.byu.cs.tweeter.presenter.SearchPresenter;

public class GetUserTask extends AsyncTask<SearchRequest, Void, SearchResponse> {
   private final SearchPresenter presenter;
   private final GetUserObserver observer;

   public interface GetUserObserver{
       void userRetrieved(SearchResponse response);
   }

    public GetUserTask(SearchPresenter presenter, GetUserObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected SearchResponse doInBackground(SearchRequest... searchRequests) {
        SearchResponse response = presenter.searchAlias(searchRequests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(SearchResponse searchResponse) {
        if(observer != null){
            observer.userRetrieved(searchResponse);
        }
    }
}
