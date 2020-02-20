package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.SignUpService;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.SignUpResponse;

public class SignUpPresenter extends Presenter {

    private final View view;
    private static String handle = "";
    private static String password = "";
    private static String firstName = "";
    private static String lastName = "";
    private static String url = "";


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

    public void updateFirstName(String name){
        this.firstName = name;
        boolean valid = checkValidFirstName(name);
        if(valid){
            checkIfComplete();
        }
    }

    public void updateLastName(String name){
        this.lastName = name;
        boolean valid = checkValidLastName(name);
        if(valid){
            checkIfComplete();
        }
    }


    private boolean checkValidPassword(String s){
        if(s.length() >= 0 && s.length() < 8){
            view.setPasswordError("Password must be at least 8 characters");
            return false;
        } else if (s.length() > 24){
            view.setPasswordError("Password must be less than 24 characters");
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
            view.setHandleError("Handle must be more than 2 characters");
            return false;
        }
    }

    private boolean checkValidFirstName(String s){
        if(s.length() > 0){
            return true;
        } else{
            view.setHandleFirst("First name is required.");
            return false;
        }
    }

    private boolean checkValidLastName(String s){
        if(s.length() > 0){
            return true;
        } else{
            view.setHandleFirst("Last name is required.");
            return false;
        }
    }

    private void checkIfComplete(){
        if(!checkValidFirstName(firstName) || !checkValidFirstName(lastName)){
            view.enableButton(false);
        } else if(url.equals("")){
            view.enableButton(false);
        } else{
            view.enableButton(true);
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

        void setHandleFirst(String error);

        void setHandleLast(String error);

        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public SignUpPresenter(View view) { this.view = view; }

    public SignUpResponse postUser(SignUpRequest request){
        return SignUpService.getInstance().postUser(request);
    }

    public String getHandle() {
        return handle;
    }

    public String getPassword() {
        return password;
    }
}
