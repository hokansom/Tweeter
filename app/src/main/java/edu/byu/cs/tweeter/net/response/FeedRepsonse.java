package edu.byu.cs.tweeter.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Feed;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedRepsonse extends PagedResponse {

    private Feed feed;

    public FeedRepsonse(String message) {
        super(false, message, false);
    }

    public FeedRepsonse(Feed feed, boolean hasMorePages){
        super(true, hasMorePages);
        this.feed = feed;
    }

    public Feed getFeed() { return feed; }
}
