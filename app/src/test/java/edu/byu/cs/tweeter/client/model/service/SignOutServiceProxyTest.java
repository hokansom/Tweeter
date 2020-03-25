package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class SignOutServiceProxyTest {

    private SignOutServiceProxy serviceProxySpy;


    @BeforeEach
    void setup(){
        serviceProxySpy = Mockito.spy(new SignOutServiceProxy());
    }

    @Test
    void testSignOut(){
        try{
            serviceProxySpy.signOut();
        } catch (IOException e){
            System.out.println(e);
        }

    }

}