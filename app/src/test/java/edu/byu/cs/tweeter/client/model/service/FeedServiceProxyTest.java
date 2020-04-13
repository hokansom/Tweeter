package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.json.Serializer;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.domain.User;

class FeedServiceProxyTest {
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user = new User("Test", "User", MALE_IMAGE_URL);
    private final String token = "123456789";
    private FeedServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new FeedServiceProxy());
    }

    @Test
    void test_getFeedHandler(){
        FeedRequest request = new FeedRequest(user, 10, null, token);
        FeedResponse response = null;
        try{
            response = serviceProxySpy.getFeed(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFeed());
        Assertions.assertEquals(10, response.getFeed().getFeed().size());
        Assertions.assertTrue(response.getHasMorePages());
    }

    @Test
    void test_withDAOgetFeedHandler(){
        FeedRequest request = new FeedRequest(user, 2, null, token);
        FeedResponse response = null;
        try{
            response = serviceProxySpy.getFeed(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFeed());
        Assertions.assertEquals(1, response.getFeed().getFeed().size());
        Assertions.assertTrue(response.getHasMorePages());
    }

    @Test
    void test_withDAO2getFeedHandler(){
        Status status = Serializer.deserialize("{\"message\":\"Testing another status\",\"author\":{\"firstName\":\"Mo\",\"lastName\":\"Davis\",\"alias\":\"Morgan\",\"imageUrl\":\"https://cs-340-w2020.s3-us-west-2.amazonaws.com/Morgan.jpg\"},\"urls\":{\"uris\":[]},\"mentions\":{\"mentions\":[]},\"date\":1586809130840}", Status.class);
        FeedRequest request = new FeedRequest(user, 2, status, token);
        FeedResponse response = null;
        try{
            response = serviceProxySpy.getFeed(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFeed());
        Assertions.assertEquals(1, response.getFeed().getFeed().size());
        Assertions.assertFalse(response.getHasMorePages());
    }



}