package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Feed;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.SignOutRequest;

class SignOutServiceProxyTest {
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user = new User("Test", "User", MALE_IMAGE_URL);

    private final String token = "e2605637-51e1-4a3d-940c-dbedc69d863e";
    private final String alias = "TestUser";

    private SignOutServiceProxy serviceProxySpy;

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

        /*The feed request requires an active token, so the feed request should fail*/
        FeedRequest feedRequest = new FeedRequest(user, 10, null, token);
        FeedServiceProxy feedServiceProxy = new FeedServiceProxy();
        try{
            feedServiceProxy.getFeed(feedRequest);
        } catch (IOException e){
            Assertions.assertEquals("[Unauthorized]: User has timed out due to inactivity", e.getMessage());
        }
    }

}