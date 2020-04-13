package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.domain.User;

class FollowingServiceProxyTest {
    private final String DEFAULT_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user = new User("Test", "User", DEFAULT_IMAGE_URL);
    private final User user2 = new User("Morgan", "Davis", "Momo", DEFAULT_IMAGE_URL);

    private FollowingServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new FollowingServiceProxy());

    }

    @Test
    void test_getFollowingHandler(){
        FollowingRequest request = new FollowingRequest(user, 10, null);
        FollowingResponse response = null;
        try{
            response = serviceProxySpy.getFollowees(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFollowees());
        Assertions.assertEquals(10, response.getFollowees().size());
        Assertions.assertTrue(response.getHasMorePages());
    }

    @Test
    void test_withDAOgetFollowingHandler(){
        FollowingRequest request = new FollowingRequest(user, 1, null);
        FollowingResponse response = null;
        try{
            response = serviceProxySpy.getFollowees(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFollowees());
        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getHasMorePages());
    }

    @Test
    void test_withDAO2getFollowingHandler(){
        FollowingRequest request = new FollowingRequest(user, 2, user2);
        FollowingResponse response = null;
        try{
            response = serviceProxySpy.getFollowees(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getFollowees());
        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertFalse(response.getHasMorePages());
    }


}