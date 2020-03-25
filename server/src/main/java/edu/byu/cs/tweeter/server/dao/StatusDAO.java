package edu.byu.cs.tweeter.server.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

/**
 * A DAO for accessing and updating 'status' data from the database.
 */
public class StatusDAO {
    private static List<User> users;
    private static Map<User, List<Status>> statusesByUser;


    /**
     * Post the status specified to the database.
     *
     * @param request contains the information about the status which is being posted
     *                and any other information required to satisfy the request
     * @return success and a message
     * */
    public StatusResponse postStatus(StatusRequest request){
        if(request.getAuthor() == null){
            return new StatusResponse(false, "[Bad Request]: User cannot be null");
        }
        if(statusesByUser == null){
            statusesByUser = initializeStatuses();
        }

        if(!users.contains(request.getAuthor())){
            return new StatusResponse(false, "[Bad Request]: Status cannot be posted by a user that does not exist");
        }


        List<Status> userStatuses = statusesByUser.get(request.getAuthor());
        if(userStatuses == null){
            userStatuses = new ArrayList<>();
        }

        if(userStatuses.contains(request.getStatus())){
            return new StatusResponse(false, "[Bad Request]: User has already posted that status.");
        }

        userStatuses.add(request.getStatus());
        statusesByUser.put(request.getAuthor(), userStatuses);
        return new StatusResponse(true, "Status posted");
    }


    /**
     * Generates the statuses data.
     */
    private Map<User, List<Status>> initializeStatuses() {
        Map<User, List<Status>> statusesByUser = new HashMap<>();
        if(users == null){
            users = new ArrayList<>();
            users.addAll(getUserGenerator().generateUsers(50));
            /*REMOVE FOR TESTING*/
            User testUser = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);
            users.add(testUser);
             /**/
        }

        List<Status> statuses = getStatusGenerator().generateAllStatuses(users, 0,5);
        for(Status status: statuses){
            List<Status> user_statuses = statusesByUser.get(status.getAuthor());
            if(user_statuses == null){
                user_statuses = new ArrayList<>();
                statusesByUser.put(status.getAuthor(), user_statuses);
            }
            user_statuses.add(status);
        }

        return statusesByUser;
    }


    /**
     * Returns an instance of StatusGenerator that can be used to generate Status data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    StatusGenerator getStatusGenerator() {
        return StatusGenerator.getInstance();
    }


    /**
     * Returns an instance of UseGenerator that can be used to generate User data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    UserGenerator getUserGenerator() { return UserGenerator.getInstance(); }
}
