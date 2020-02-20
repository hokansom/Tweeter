package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.SignInRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.SignInResponse;
import edu.byu.cs.tweeter.net.response.SignUpResponse;
import edu.byu.cs.tweeter.presenter.SignInPresenter;
import edu.byu.cs.tweeter.presenter.SignUpPresenter;

public class PostSignInTask extends AsyncTask<SignInRequest, Void, SignInResponse> {

    private final SignInPresenter presenter;
    private final PostSignInObserver observer;

    public interface PostSignInObserver{
        void signInPosted(SignInResponse response);
    }

    public PostSignInTask(SignInPresenter presenter, PostSignInObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected SignInResponse doInBackground(SignInRequest... signInRequests) {
        SignInResponse response = presenter.postSignIn(signInRequests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(SignInResponse signInResponse) {
        if(observer != null){
            observer.signInPosted(signInResponse);
        }
    }
}
