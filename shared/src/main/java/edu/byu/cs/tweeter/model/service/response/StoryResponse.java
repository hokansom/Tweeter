package edu.byu.cs.tweeter.model.service.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.Story;

public class StoryResponse extends PagedResponse {

    public Story story;

    public StoryResponse(String message) {
        super(false, message, false);
    }

    public StoryResponse(Story story, boolean hasMorePages){
        super(true, hasMorePages);
        this.story = story;
    }

    public Story getStory() { return story; }
}
