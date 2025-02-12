package edu.byu.cs.tweeter.presenter.status;


import edu.byu.cs.tweeter.model.services.StatusService;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

public class StatusPresenter extends AbstractStatusPresenter {
    private final View view;
    private int MAX_CHAR = 280;

    public void checkStatus(String s){
        int length = s.length();
        if(length > MAX_CHAR){
            updateView("Message must be less than 280 characters.", false, length);

        } else if(length == 0){
            updateView("Message cannot be empty.", false, length);

        } else{
            updateView(null, true, length);
        }
    }

    private void updateView(String message, boolean enabled, int count){
        view.handleEditing(message);
        view.enableShare(enabled);
        view.updateCounts( String.format("%d", count));
    }

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void updateCounts(String chars);
        void handleEditing(String error);
        void enableShare(boolean enable);
    }

    public StatusPresenter(View view) {this.view = view;}

    public StatusResponse postStatus(StatusRequest request){
        return StatusService.getInstance().postStatus(request);
    }

}
