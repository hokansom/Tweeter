package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.SignInService;
import edu.byu.cs.tweeter.net.request.SignInRequest;
import edu.byu.cs.tweeter.net.response.SignInResponse;

public class SignInPresenter extends Presenter {
    private final View view;
    private String handle = "";
    private String password = "";


    public void updateHandle(String handle){
        this.handle = handle;
        checkValidity();
    }

    public void updatePassword(String password){
        this.password = password;
        checkValidity();
    }

    private void checkValidity(){
        if(!password.equals("") && !handle.equals("")){
            view.enableButton(true);
        }
        else{
            view.enableButton(false);
        }
    }

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void enableButton(Boolean enabled);
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public SignInPresenter(View view){ this.view = view; }

    public SignInResponse postSignIn(SignInRequest request){
        return SignInService.getInstance().postSignIn(request);
    }

}
