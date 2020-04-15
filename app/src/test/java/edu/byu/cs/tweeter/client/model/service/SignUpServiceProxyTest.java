package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.UUID;

import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.model.domain.User;

class SignUpServiceProxyTest {
    private final String DEFAULT_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User existingUser = new User("Morgan", "Davis", "Momo", DEFAULT_URL );

    private SignUpServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new SignUpServiceProxy());
    }

    @Test
    void test_postSignUpHandler(){
        String randomAlias = UUID.randomUUID().toString();
        User newUser = new User("Testing", "SignUp", randomAlias, "");
        SignUpRequest request = new SignUpRequest(newUser, "Password", "");
        SignUpResponse response = null;

        try{
            response = serviceProxySpy.postSignUp(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertEquals(newUser, response.getUser());
        Assertions.assertEquals(DEFAULT_URL, response.getUser().getImageUrl());
        Assertions.assertNotNull(response.getToken());
        Assertions.assertNotEquals("", response.getToken());

    }

    @Test
    void test_postSignUp_exisitingUser(){
        SignUpRequest request = new SignUpRequest(existingUser, "Password", "");
        SignUpResponse response = null;

        try{
            response = serviceProxySpy.postSignUp(request);
        } catch (IOException e){
            Assertions.assertEquals("[Bad Request]: The alias (@Momo) is already taken.", e.getMessage());
        }

        Assertions.assertNull(response);
    }
}