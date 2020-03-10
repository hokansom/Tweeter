package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.presenter.signUp.AbstractSignUpPresenter;
import edu.byu.cs.tweeter.presenter.signUp.SignUpPresenter;

public class PostSignUpTask extends AsyncTask<SignUpRequest, Void, SignUpResponse> {

    private final AbstractSignUpPresenter presenter;
    private final PostSignUpObserver observer;

    public interface PostSignUpObserver{
        void signUpPosted(SignUpResponse response);
    }

    public PostSignUpTask(SignUpPresenter presenter, PostSignUpObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected SignUpResponse doInBackground(SignUpRequest... signUpRequests) {
        SignUpResponse response = presenter.postUser(signUpRequests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(SignUpResponse signUpResponse) {
        if(observer != null){
            observer.signUpPosted(signUpResponse);
        }
    }
}
