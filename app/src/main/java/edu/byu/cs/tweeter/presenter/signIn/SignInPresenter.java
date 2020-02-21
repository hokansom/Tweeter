package edu.byu.cs.tweeter.presenter.signIn;

import edu.byu.cs.tweeter.model.services.SignInService;
import edu.byu.cs.tweeter.net.request.SignInRequest;
import edu.byu.cs.tweeter.net.response.SignInResponse;

public class SignInPresenter extends AbstractSignInPresenter {
    private final View view;
    private String handle = "";
    private String password = "";


    public void updateHandle(String handle){
        this.handle = handle;
        boolean valid = checkValidHandle(handle);
        if(valid){
            updateButton();
        }
    }

    public void updatePassword(String password){
        this.password = password;
        boolean valid = checkValidPassword(password);
        if(valid){
            updateButton();
        }
    }

    private boolean checkValidPassword(String s){
        if(s.length() >= 0 && s.length() < 8){
            view.setPasswordError("Password is at least 8 characters");
            return false;
        } else if (s.length() > 24){
            view.setPasswordError("Password exceeds character limit");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkValidHandle(String s){
        if(s.length() > 0 && !s.startsWith("@")){
            view.setHandleError("Handle must start with '@'");
            return false;
        } else if (s.length() > 2){
            return true;
        }else{
            view.setHandleError("Handle required");
            return false;
        }
    }

    private void updateButton(){
        if(checkValidPassword(password) && checkValidHandle(handle)){
            view.enableButton(true);
        } else{
            view.enableButton(false);
        }
    }

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void enableButton(Boolean enabled);

        void setPasswordError(String error);

        void setHandleError(String error);
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public SignInPresenter(View view){ this.view = view; }

    public SignInResponse postSignIn(SignInRequest request){
        return SignInService.getInstance().postSignIn(request);
    }

}
