package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.json.Serializer;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;

class FeedServiceProxyTest {
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user1 = new User("Test", "User", MALE_IMAGE_URL);
    private final User user2 = new User("Mo", "Davis", "Morgan", "https://cs-340-w2020.s3-us-west-2.amazonaws.com/Morgan.jpg");
    private final User user3 = new User("Morgan", "Pleasework", "Testing", "https://cs-340-w2020.s3-us-west-2.amazonaws.com/Testing.jpg");

    private static String user1Token;
    private static String user2Token;
    private static String user3Token;
    private FeedServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new FeedServiceProxy());
        try{
            SignInServiceProxy signInServiceProxy = new SignInServiceProxy();

            SignInRequest signInRequest = new SignInRequest("TestUser", "Password");
            SignInResponse response = signInServiceProxy.postSignIn(signInRequest);
            user1Token = response.getToken();

            signInRequest = new SignInRequest("Morgan", "Password");
            response = signInServiceProxy.postSignIn(signInRequest);
            user2Token = response.getToken();

            signInRequest = new SignInRequest("Testing", "Password");
            response = signInServiceProxy.postSignIn(signInRequest);
            user3Token = response.getToken();

        } catch (IOException e){

        }

    }

    @Test
    void test_getFeedHandler(){
        FeedRequest request = new FeedRequest(user1, 10, null, user1Token);
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
    void test_getFeedHandler_aboveLimit(){
        FeedRequest request = new FeedRequest(user2, 5, null, user2Token);
        FeedResponse response = null;
        try{
            response = serviceProxySpy.getFeed(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFeed());
        Assertions.assertEquals(5, response.getFeed().getFeed().size());
        Assertions.assertTrue(response.getHasMorePages());

        Status lastStatus = response.getFeed().getFeed().get(4);

        request = new FeedRequest(user2, 5, lastStatus, user2Token);
        try{
            response = serviceProxySpy.getFeed(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFeed());
        Assertions.assertEquals(5, response.getFeed().getFeed().size());
        Assertions.assertTrue(response.getHasMorePages());
        for(Status status: response.getFeed().getFeed()){
            Assertions.assertNotEquals(lastStatus, status);
        }
    }

    @Test
    void test_getFeedHandler_noFeed(){
        FeedRequest request = new FeedRequest(user3, 10, null, user3Token);
        FeedResponse response = null;
        try{
            response = serviceProxySpy.getFeed(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFeed());
        Assertions.assertEquals(0, response.getFeed().getFeed().size());
        Assertions.assertFalse(response.getHasMorePages());
    }



}