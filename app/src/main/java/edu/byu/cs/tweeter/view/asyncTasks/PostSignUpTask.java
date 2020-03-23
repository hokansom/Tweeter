package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.presenter.signUp.AbstractSignUpPresenter;
import edu.byu.cs.tweeter.presenter.signUp.SignUpPresenter;

public class PostSignUpTask extends AsyncTask<SignUpRequest, Void, SignUpResponse> {

    private final AbstractSignUpPresenter presenter;
    private final PostSignUpObserver observer;

    private Exception exception;

    public interface PostSignUpObserver{
        void signUpPosted(SignUpResponse response);
        void handleException(Exception ex);
    }

    public PostSignUpTask(SignUpPresenter presenter, PostSignUpObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected SignUpResponse doInBackground(SignUpRequest... signUpRequests) {
        SignUpResponse response = null;
        try{
            response = presenter.postSignUp(signUpRequests[0]);
        } catch(IOException e){
            exception = e;
        }
        return response;
    }

    @Override
    protected void onPostExecute(SignUpResponse signUpResponse) {
        if(observer != null){
            if(exception == null){
                observer.signUpPosted(signUpResponse);
            } else {
                observer.handleException(exception);
            }

        }
    }
}
