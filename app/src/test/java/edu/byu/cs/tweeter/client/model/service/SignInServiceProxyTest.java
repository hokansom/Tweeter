package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.model.domain.User;

class SignInServiceProxyTest {
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user = new User("Test", "User", MALE_IMAGE_URL);

    private SignInServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new SignInServiceProxy());
    }

    @Test
    void test_postSignInHandler(){
        SignInRequest request = new SignInRequest(user.getAlias(), "Password");
        SignInResponse response = null;

        try{
            response = serviceProxySpy.postSignIn(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertEquals(user, response.getUser());
        Assertions.assertNotNull(response.getToken());

    }


}