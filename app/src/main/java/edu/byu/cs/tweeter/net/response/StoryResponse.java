package edu.byu.cs.tweeter.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.Story;

public class StoryResponse extends PagedResponse {

    private Story story;

    public StoryResponse(String message) {
        super(false, message, false);
    }

    public StoryResponse(Story story, boolean hasMorePages){
        super(true, hasMorePages);
        this.story = story;
    }

    public Story getStory() { return story; }
}
