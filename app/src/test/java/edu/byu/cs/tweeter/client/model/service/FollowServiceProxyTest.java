package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;

class FollowServiceProxyTest {
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user1 = new User("Test", "User", MALE_IMAGE_URL);
    private final User user2 = new User("Alonzo", "Cadden", MALE_IMAGE_URL);
    private final User user3 = new User("Mo", "Davis", "Morgan", "https://cs-340-w2020.s3-us-west-2.amazonaws.com/Morgan.jpg");

    private FollowServiceProxy serviceProxySpy;
    private UnfollowServiceProxy unfollowServiceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new FollowServiceProxy());
        unfollowServiceProxySpy = Mockito.spy(new UnfollowServiceProxy());

    }

    @Test
    void testFollowHandler_unfollow(){
        Follow follow = new Follow(user1, user2);
        FollowRequest request = new FollowRequest(follow, false, "Authorization");
        FollowResponse response = null;
        try{
            response = serviceProxySpy.postFollow(request);
        } catch(IOException e){
            System.out.println(e);
        }

//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(FollowResponse.class, response.getClass());
    }

    @Test
    void testFollowHandler_follow(){
        Follow follow = new Follow(user2, user1);
        FollowRequest request = new FollowRequest(follow, true, "AuthToken");
        FollowResponse response = null;
        try{
            response = serviceProxySpy.postFollow(request);
        } catch(IOException e){
            System.out.println(e);
        }

//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(FollowResponse.class, response.getClass());
    }

    @Test
    void myTest_follow(){
        User test1 = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User test2 = new User("Morgan", "Davis", "Momo", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        Follow follow = new Follow(test1, test2);
        FollowRequest request = new FollowRequest(follow, true, "123456789");
        FollowResponse response = null;
        try{
            response = serviceProxySpy.postFollow(request);
        } catch(IOException e){
            System.out.println(e);
        }
    }

    @Test
    void myTest2_follow(){
        User test1 = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        Follow follow = new Follow(test1, user3);
        FollowRequest request = new FollowRequest(follow, true, "123456789");
        FollowResponse response = null;
        try{
            response = serviceProxySpy.postFollow(request);
        } catch(IOException e){
            System.out.println(e);
        }
    }

    @Test
    void myTest_unfollow(){
        User test1 = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User test2 = new User("Morgan", "Davis", "Momo", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        Follow follow = new Follow(test1, test2);
        FollowRequest request = new FollowRequest(follow, false, "123456789");
        FollowResponse response = null;
        try{
            response = unfollowServiceProxySpy.deleteFollow(request);
        } catch(IOException e){
            System.out.println(e);
        }
    }

}