package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.server.dao.StatusDAOMock;

public class StatusServiceImpl implements StatusService {


    @Override
    public StatusResponse postStatus(StatusRequest request) {
        StatusDAOMock dao = new StatusDAOMock();
        return dao.postStatus(request);
    }

    public void addStatusToQueue(Status status){
        SQSService service = new SQSService();
        service.addStatusToQueue(status);
    }
}
