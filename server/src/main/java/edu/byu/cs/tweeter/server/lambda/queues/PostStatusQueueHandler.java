package edu.byu.cs.tweeter.server.lambda.queues;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.json.Serializer;
import edu.byu.cs.tweeter.server.service.FollowerServiceImpl;


public class PostStatusQueueHandler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        FollowerServiceImpl service = new FollowerServiceImpl();
        for(SQSEvent.SQSMessage msg : sqsEvent.getRecords()){

            Status status = Serializer.deserialize(msg.getBody(), Status.class);
            service.getAllFollowers(status);
        }
        return null;
    }
}