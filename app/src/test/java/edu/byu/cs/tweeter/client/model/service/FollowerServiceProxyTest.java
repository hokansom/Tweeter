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
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user = new User("Test", "User", MALE_IMAGE_URL);

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

}