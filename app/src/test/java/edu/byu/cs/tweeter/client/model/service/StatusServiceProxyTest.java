package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;


class StatusServiceProxyTest {
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user1 = new User("Morgan", "Davis","Momo", MALE_IMAGE_URL );
    private final User user2 =  new User("Morgan", "Pleasework", "Testing", "https://cs-340-w2020.s3-us-west-2.amazonaws.com/Testing.jpg");

    private String user1Token = "123456789";
    private String user2Token = "testing";
    private final Status status1 = new Status(user1, "Testing status 1 @TestUser www.google.com");
    private final Status status2 = new Status(user2, "Status created from a test!");
//    private final Status status3 = new Status(user2, "Will it work again?");
    private StatusServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new StatusServiceProxy());
        try{
            SignInServiceProxy signInServiceProxy = new SignInServiceProxy();

            SignInRequest signInRequest = new SignInRequest("Momo", "Password");
            SignInResponse response = signInServiceProxy.postSignIn(signInRequest);
            user1Token = response.getToken();

            signInRequest = new SignInRequest("Testing", "Password");
            response = signInServiceProxy.postSignIn(signInRequest);
            user2Token = response.getToken();


        } catch (IOException e){

        }
    }

    @Test
    void testPostStatusHandler(){
        StatusRequest request = new StatusRequest(user1, status1, user1Token);
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
        StatusRequest request = new StatusRequest(user2, status2, user2Token);
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