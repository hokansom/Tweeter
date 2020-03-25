package edu.byu.cs.tweeter.model.service.response;

import java.util.ArrayList;

import edu.byu.cs.tweeter.model.domain.Story;

public class StoryResponse extends PagedResponse {

    public Story story;

    public StoryResponse(String message) {
        super(false, message, false);
        this.story = new Story(new ArrayList<>(), null);
    }

    public StoryResponse(Story story, boolean hasMorePages){
        super(true, hasMorePages);
        this.story = story;
    }

    public Story getStory() { return story; }
}
