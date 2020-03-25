package edu.byu.cs.tweeter.server.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusGenerator {

    private static List<String>  samples = new ArrayList<String>(Arrays.asList("Status 1 @TestUser www.google.com", "Status 2  @TestUser www.facebook.com" , "Status 3 @TestUser", "Status 4  @TestUser", "Status 5 @TestUser"));


    private static StatusGenerator statusGenerator;

    private StatusGenerator() {

    }


    public static StatusGenerator getInstance(){
        if(statusGenerator == null){
            statusGenerator = new StatusGenerator();
        }
        return statusGenerator;
    }


    public List<Status> generateAllStatuses(List<User> users, int minNumOfStatuses, int maxNumOfStatuses){
        List<Status> statuses = new ArrayList<>();
        for(User user: users){
            List<Status> temp = generateUserStatuses(user, minNumOfStatuses, maxNumOfStatuses);
            statuses.addAll(temp);
        }
        return statuses;
    }

    private List<Status> generateUserStatuses( User user, int minNumOfStatuses, int maxNumOfStatuses){
        List<Status> statuses = new ArrayList<>();

        assert minNumOfStatuses >= 0 : minNumOfStatuses;
        assert maxNumOfStatuses > minNumOfStatuses: maxNumOfStatuses;

        Random random = new Random();
        int numStatusToGenerate = random.nextInt(maxNumOfStatuses - minNumOfStatuses) + minNumOfStatuses;

        for(int i = 0; i < numStatusToGenerate; i++){
            statuses.add(generateStatus(user));
        }

        Collections.sort(statuses);

        return statuses;
    }

    private Status generateStatus(User user){
        Random random = new Random();

        String message = samples.get(random.nextInt(samples.size()));

        Status status = new Status(user, message);

        return status;
    }
}


