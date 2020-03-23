package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.presenter.signIn.AbstractSignInPresenter;
import edu.byu.cs.tweeter.presenter.signIn.SignInPresenter;

public class PostSignInTask extends AsyncTask<SignInRequest, Void, SignInResponse> {

    private final AbstractSignInPresenter presenter;
    private final PostSignInObserver observer;

    private Exception exception;

    public interface PostSignInObserver{
        void signInPosted(SignInResponse response);
        void handleException(Exception ex);
    }

    public PostSignInTask(SignInPresenter presenter, PostSignInObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected SignInResponse doInBackground(SignInRequest... signInRequests) {
        SignInResponse response = null;
        try{
            response = presenter.postSignIn(signInRequests[0]);
        } catch (IOException e){
            exception = e;
        }
        return response;
    }

    @Override
    protected void onPostExecute(SignInResponse signInResponse) {
        if(observer != null){
            if(exception == null){
                observer.signInPosted(signInResponse);
            } else {
                observer.handleException(exception);
            }

        }
    }
}
