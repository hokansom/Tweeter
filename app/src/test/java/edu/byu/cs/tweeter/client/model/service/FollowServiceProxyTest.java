package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;

class FollowServiceProxyTest {
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user1 = new User("Test", "User", MALE_IMAGE_URL);
    private final User user2 = new User("Mo", "Davis", "Morgan", "https://cs-340-w2020.s3-us-west-2.amazonaws.com/Morgan.jpg");
    private final User user3 = new User("Morgan", "Pleasework", "Testing", "https://cs-340-w2020.s3-us-west-2.amazonaws.com/Testing.jpg");

    private String user1Token;
    private String user2Token;

    private FollowServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new FollowServiceProxy());
        try{
            SignInServiceProxy signInServiceProxy = new SignInServiceProxy();

            SignInRequest signInRequest = new SignInRequest("TestUser", "Password");
            SignInResponse response = signInServiceProxy.postSignIn(signInRequest);
            user1Token = response.getToken();

            signInRequest = new SignInRequest("Morgan", "Password");
            response = signInServiceProxy.postSignIn(signInRequest);
            user2Token = response.getToken();


        } catch (IOException e){

        }

    }

    @Test
    void test_followHandler_follow(){
        Follow follow = new Follow(user2, user3);
        FollowRequest request = new FollowRequest(follow, true, user2Token);
        FollowResponse response = null;
        try{
            response = serviceProxySpy.postFollow(request);
        } catch(IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertEquals(FollowResponse.class, response.getClass());
    }

    @Test
    void test_followHandler_existingFollow(){
        Follow follow = new Follow(user1, user2);
        FollowRequest request = new FollowRequest(follow, true, user1Token);
        FollowResponse response = null;
        try{
            response = serviceProxySpy.postFollow(request);
        } catch(IOException e){
            Assertions.assertEquals("[Bad Request]: follow relationship already exists", e.getMessage());
        }

        Assertions.assertNull(response);
    }

    @AfterEach
    void tearDown() {
        UnfollowServiceProxy unfollowServiceProxy = new UnfollowServiceProxy();
        FollowRequest request = new FollowRequest(new Follow(user2, user3), false, user2Token);
        try{
            unfollowServiceProxy.deleteFollow(request);
        } catch (IOException e){

        }
    }
}