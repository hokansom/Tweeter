package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SignOutRequest;

class SignOutServiceProxyTest {

    private SignOutServiceProxy serviceProxySpy;
    private final String token = "f8e95774-7c75-45c1-a9cf-6bce0060897d";
    private final String alias = "TestUser";

    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new SignOutServiceProxy());
    }

    @Test
    void testSignOut(){
        SignOutRequest request = new SignOutRequest(alias, token);
        try{
            serviceProxySpy.signOut(request);
        } catch (IOException e){
            System.out.println(e);
        }

    }

}