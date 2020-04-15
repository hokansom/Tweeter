package edu.byu.cs.tweeter.server.lambda.queues;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.cs.tweeter.model.domain.QueueMessage;
import edu.byu.cs.tweeter.server.json.Serializer;
import edu.byu.cs.tweeter.server.service.FeedServiceImpl;

public class UpdateFeedQueueHandler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        System.out.println("Received an event");
        FeedServiceImpl service = new FeedServiceImpl();
        System.out.println(sqsEvent.getRecords().size());
        for(SQSEvent.SQSMessage msg : sqsEvent.getRecords()){
            System.out.println(msg.getBody());
            QueueMessage queueMessage = Serializer.deserialize(msg.getBody(), QueueMessage.class);
            service.updateFeeds(queueMessage.getStatus(), queueMessage.getAliases());
        }
        return null;
    }
}
