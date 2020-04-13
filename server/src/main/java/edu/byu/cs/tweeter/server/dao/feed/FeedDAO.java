package edu.byu.cs.tweeter.server.dao.feed;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public interface FeedDAO {
    /**
     * Gets the feed from the database for the user specified in the request. Uses
     * information in the request object to limit the number of statuses returned and to return the
     * next set of statuses after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose feed is to be returned and any
     *                other information required to satisfy the request.
     * @return the feed (list of statuses).
     */
    FeedResponse getFeed(FeedRequest request);
}
