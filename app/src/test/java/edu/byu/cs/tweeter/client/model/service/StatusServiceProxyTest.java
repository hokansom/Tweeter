package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;


class StatusServiceProxyTest {
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user = new User("Test", "User", MALE_IMAGE_URL);
    private final User user2 = new User("Morgan", "Davis","Momo", MALE_IMAGE_URL );

    private final String auth = "123456789";
    private final Status status1 = new Status(user, "Testing status 1 @TestUser www.google.com");
    private final Status status2 = new Status(user2, "Please work!");
    private final Status status3 = new Status(user2, "Will it work again?");
    private StatusServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new StatusServiceProxy());
    }

    @Test
    void testPostStatusHandler(){
        StatusRequest request = new StatusRequest(user, status1, auth);
        StatusResponse response = null;
        try{
            response = serviceProxySpy.postStatus(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.success);
    }

    @Test
    void testWithDAO_PostStatusHandler(){
        StatusRequest request = new StatusRequest(user2, status3, auth);
        StatusResponse response = null;
        try{
            response = serviceProxySpy.postStatus(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.success);
    }


}