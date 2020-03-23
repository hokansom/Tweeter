package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.presenter.search.AbstractSearchPresenter;
import edu.byu.cs.tweeter.presenter.search.SearchPresenter;

public class GetUserTask extends AsyncTask<SearchRequest, Void, SearchResponse> {
   private final AbstractSearchPresenter presenter;
   private final GetUserObserver observer;

    private Exception exception;

   public interface GetUserObserver{
       void userRetrieved(SearchResponse response);
       void handleException(Exception ex);
   }

    public GetUserTask(SearchPresenter presenter, GetUserObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected SearchResponse doInBackground(SearchRequest... searchRequests) {
        SearchResponse response = null;
        try{
            response = presenter.getUser(searchRequests[0]);
        } catch (IOException e){
            exception = e;
        }
        return response;
    }

    @Override
    protected void onPostExecute(SearchResponse searchResponse) {
        if(observer != null){
            if(exception == null){
                observer.userRetrieved(searchResponse);
            } else {
                observer.handleException(exception);
            }
        }
    }
}
