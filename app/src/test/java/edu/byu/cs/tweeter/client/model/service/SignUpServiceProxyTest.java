package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.model.domain.User;

class SignUpServiceProxyTest {

    private SignUpServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new SignUpServiceProxy());
    }

    @Test
    void test_postSignUpHandler(){
        User newUser = new User("Morgan", "Davis", "Momo", "");
        SignUpRequest request = new SignUpRequest(newUser, "Password", "");
        SignUpResponse response = null;

        try{
            response = serviceProxySpy.postSignUp(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertEquals(newUser, response.getUser());
        Assertions.assertNotNull(response.getToken());

    }
}