package edu.byu.cs.tweeter.presenter;


import edu.byu.cs.tweeter.model.services.StatusService;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.StatusResponse;

public class StatusPresenter extends Presenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public StatusPresenter(View view) {this.view = view;}

    public StatusResponse postStatus(StatusRequest request){
        return StatusService.getInstance().postStatus(request);
    }

}
