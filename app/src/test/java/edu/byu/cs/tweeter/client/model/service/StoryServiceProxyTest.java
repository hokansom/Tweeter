package edu.byu.cs.tweeter.client.model.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.json.Serializer;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.domain.User;

class StoryServiceProxyTest {
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user = new User("Test", "User", MALE_IMAGE_URL);
    private final User user2 = new User("Mo", "Davis", "Morgan", "https://cs-340-w2020.s3-us-west-2.amazonaws.com/Morgan.jpg");
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

    @Test
    void test_withDAOgetStoryHandler(){
        StoryRequest request = new StoryRequest(user2, 1, null);
        StoryResponse response = null;
        try{
            response = serviceProxySpy.getStory(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getStory());
        Assertions.assertEquals(1, response.getStory().getStory().size());
        Assertions.assertTrue(response.getHasMorePages());
    }

    @Test
    void test_withDAO2getStoryHandler(){
        Status status = Serializer.deserialize("{\"message\":\"Testing another status\",\"author\":{\"firstName\":\"Mo\",\"lastName\":\"Davis\",\"alias\":\"Morgan\",\"imageUrl\":\"https://cs-340-w2020.s3-us-west-2.amazonaws.com/Morgan.jpg\"},\"urls\":{\"uris\":[]},\"mentions\":{\"mentions\":[]},\"date\":1586809130840}", Status.class);
        StoryRequest request = new StoryRequest(user2, 2, status);
        StoryResponse response = null;
        try{
            response = serviceProxySpy.getStory(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getStory());
        Assertions.assertEquals(1, response.getStory().getStory().size());
        Assertions.assertFalse(response.getHasMorePages());
    }
}