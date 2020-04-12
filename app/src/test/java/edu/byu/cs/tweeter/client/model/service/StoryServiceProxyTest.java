package edu.byu.cs.tweeter.client.model.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.domain.User;

class StoryServiceProxyTest {
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user = new User("Test", "User", MALE_IMAGE_URL);
    private StoryServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new StoryServiceProxy());

    }

    @Test
    void test_getStoryHandler(){
        StoryRequest request = new StoryRequest(user, 10, null);
        StoryResponse response = null;
        try{
            response = serviceProxySpy.getStory(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getStory());
        Assertions.assertNotEquals(0, response.getStory().getStory().size());
        Assertions.assertFalse(response.getHasMorePages());
    }
}