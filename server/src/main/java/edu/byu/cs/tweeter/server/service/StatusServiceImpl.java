package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.server.dao.StatusDAO;

public class StatusServiceImpl implements StatusService {


    @Override
    public StatusResponse postStatus(StatusRequest request) {
        StatusDAO dao = new StatusDAO();
        return dao.postStatus(request);
    }
}
