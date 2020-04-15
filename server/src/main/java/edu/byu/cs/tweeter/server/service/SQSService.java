package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.BatchResultErrorEntry;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageBatchResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import edu.byu.cs.tweeter.model.domain.QueueMessage;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.json.Serializer;

public class SQSService {
    private final String PostStatusURL = "https://sqs.us-west-2.amazonaws.com/040510948391/PostStatusQueue";
    private final String UpdateFeedURL = "https://sqs.us-west-2.amazonaws.com/040510948391/UpdateFeedQueue";

    private final AmazonSQSAsync sqs = AmazonSQSAsyncClientBuilder.defaultClient();

    public void addStatusToQueue(Status status){
        String message = Serializer.serialize(status);
        sendMessage(message, PostStatusURL);
    }

    public void postToFeedQueue(List<String> aliases, Status status){
        System.out.println("Reached postToFeedQueue");
        List<String> batchList = new ArrayList<>();
        List<SendMessageBatchRequestEntry> entries = new ArrayList<>();
        int counter = 1;
        for(String alias: aliases){
            batchList.add(alias);

            if(batchList.size() == 25){
                String msgName = "msg" + counter;
                QueueMessage queueMessage = new QueueMessage(status, batchList);
                String message = Serializer.serialize(queueMessage);

                SendMessageBatchRequestEntry entry = new SendMessageBatchRequestEntry(msgName, message);
                entries.add(entry);

                counter++;
                batchList = new ArrayList<>();
            }
            if(entries.size() == 10){
                sendBatch(entries, UpdateFeedURL);
                entries = new ArrayList<>();
            }
        }

        /*Add any leftover aliases to a new batch*/
        if (batchList.size() > 0){
            QueueMessage queueMessage = new QueueMessage(status, batchList);
            String message = Serializer.serialize(queueMessage);
            String msgName = "msg" + counter;
            SendMessageBatchRequestEntry entry = new SendMessageBatchRequestEntry(msgName, message);
            entries.add(entry);
        }

        /*Send any remaining batches */
        if(entries.size() > 0){
            sendBatch(entries, UpdateFeedURL);
        }
    }

    private void sendMessage(String message, String url){
        try{
            System.out.println("Message to send to SQS sendmessage: message" + message);
            SendMessageRequest request = new SendMessageRequest()
                    .withQueueUrl(url)
                    .withMessageBody(message);

            SendMessageResult result = sqs.sendMessage(request);
            System.out.println(result.getSdkResponseMetadata());
            System.out.println(result.getMessageId());

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("[Internal Service Error]: Could not send SQS message");
        }
    }

    private void sendBatch(List<SendMessageBatchRequestEntry> entries, String url){
        if(entries.size() > 0){
            try{
                SendMessageBatchRequest request = new SendMessageBatchRequest(url, entries);

                Future<SendMessageBatchResult> resultFuture = sqs.sendMessageBatchAsync(request);
                System.out.println("Batch sent");
                while(resultFuture.get().getFailed().size() > 0){
                    System.out.println("An error occurred while trying to send batch");
                    for(BatchResultErrorEntry entry : resultFuture.get().getFailed()){
                        System.out.println(entry.getMessage());
                    }
                }

            } catch(Exception e){
                e.printStackTrace();
                throw new RuntimeException("[Internal Service Error]: Could not send batch message to queue");
            }
        }
    }
}
