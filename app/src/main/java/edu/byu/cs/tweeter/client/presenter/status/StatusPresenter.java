package edu.byu.cs.tweeter.client.presenter.status;


import java.io.IOException;

import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.StatusServiceProxy;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

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

    public StatusResponse postStatus(StatusRequest request) throws IOException {
        StatusService service = new StatusServiceProxy();
        return service.postStatus(request, getToken());
    }

}
