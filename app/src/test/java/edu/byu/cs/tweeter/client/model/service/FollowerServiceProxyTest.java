package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.domain.User;

class FollowerServiceProxyTest {
    private final String DEFAULT_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user = new User("Test", "User", DEFAULT_IMAGE_URL);
    private final User user2 = new User("Morgan", "Davis", "Momo", DEFAULT_IMAGE_URL); 
    private FollowerServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new FollowerServiceProxy());
    }

    @Test
    void test_getFollowersHandler(){
        FollowerRequest request = new FollowerRequest(user, 10, null);
        FollowerResponse response = null;
        try{
            response = serviceProxySpy.getFollowers(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFollowers());
        Assertions.assertEquals(10, response.getFollowers().size());
        Assertions.assertTrue(response.getHasMorePages());

    }

    @Test
    void test_withDAOgetFollowersHandler(){
        FollowerRequest request = new FollowerRequest(user2, 1, null);
        FollowerResponse response = null;
        try{
            response = serviceProxySpy.getFollowers(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFollowers());
        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertTrue(response.getHasMorePages());

    }

    @Test
    void test_withDAO2getFollowersHandler(){
        User temp = new User("Mo", "Davis", "Morgan", "https://cs-340-w2020.s3-us-west-2.amazonaws.com/Morgan.jpg");
        FollowerRequest request = new FollowerRequest(user2, 2, temp);
        FollowerResponse response = null;
        try{
            response = serviceProxySpy.getFollowers(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFollowers());
        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertFalse(response.getHasMorePages());

    }

}