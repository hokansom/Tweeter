package edu.byu.cs.tweeter.server.dao;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

/**
 * A DAO for accessing 'story' data from the database.
 */
public class StoryDAO {
    private static Map<User, List<Status>> statusesByUser;

    private static List<User> users;

    /**
     * Gets the story from the database for the user specified in the request. Uses
     * information in the request object to limit the number of statuses returned and to return the
     * next set of statuses after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose story is to be returned and any
     *                other information required to satisfy the request.
     * @return the story (list of statuses).
     */

    public StoryResponse getStory(StoryRequest request){
        List<Status> all_statuses = new ArrayList<>();
        List<Status> final_statuses = new ArrayList<>(request.getLimit());
        assert request.getLimit() >= 0;
        assert request.getUser() != null;
        int endingIndex = 0;
        if(statusesByUser == null){
            statusesByUser = initializeStatuses();
        }
        if(request.getLimit() > 0){
            all_statuses = statusesByUser.get(request.getUser());
            if(all_statuses == null){
                return new StoryResponse("[Bad Request]: User does not exist");
            }
            int statusIndex = getStatusStartingIndex(request.getLastStatus(), all_statuses);
            final_statuses = getPagedStatuses(request.getLimit(), statusIndex, all_statuses);
            endingIndex = statusIndex + final_statuses.size();
        }

        Story story = new Story(final_statuses, request.getUser());

        boolean hasMorePages = endingIndex < all_statuses.size();

        return new StoryResponse(story, hasMorePages);
    }

    /**
     * Generates the statuses data.
     */
    private Map<User, List<Status>> initializeStatuses() {
        Map<User, List<Status>> statusesByUser = new HashMap<>();
        if(users == null){
            users = getUserGenerator().generateUsers(50);
        }

        List<Status> statuses = getStatusGenerator().generateAllStatuses(users, 1,5);
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
     * Sorts the statuses data.
     */
    private List<Status> sortStatuses(List<Status> statuses){
        Collections.sort(statuses, Collections.<Status>reverseOrder());
        return statuses;
    }

    /**
     * Determines the index for the first status in the specified 'feed' list that should
     * be returned in the current request. This will be the index of the next status after the
     * specified 'lastStatus'.
     *
     * @param lastStatus the last status that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allStatuses the generated list of statuses from which we are returning paged results.
     * @return the index of the first status to be returned.
     */
    private int getStatusStartingIndex(Status lastStatus, List<Status> allStatuses){
        int statusIndex = 0;

        if(lastStatus != null){
            for(int i = 0; i < allStatuses.size(); i++){
                if(lastStatus.equals(allStatuses.get(i))){
                    statusIndex = i + 1;
                    break;
                }
            }
        }

        return statusIndex;
    }

    /**
     * Determines the index for the first status in the specified 'feed' list that should
     * be returned in the current request. This will be the index of the next status after the
     * specified 'lastStatus'.
     *
     * @param limit the amount of statuses to be returned.
     * @param statusIndex the starting index to get statuses from
     * @param statuses all statuses in a given user's feed
     * @return the feed
     */
    private List<Status> getPagedStatuses(int limit, int statusIndex, List<Status> statuses){
        statuses = sortStatuses(statuses);
        List<Status> desiredStatuses = new ArrayList<>(limit);

        if(null != statuses ){
            for(int limitCounter = 0; statusIndex < statuses.size() && limitCounter < limit; limitCounter++, statusIndex++){
                desiredStatuses.add(statuses.get(statusIndex));
            }
        }
        return desiredStatuses;
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
