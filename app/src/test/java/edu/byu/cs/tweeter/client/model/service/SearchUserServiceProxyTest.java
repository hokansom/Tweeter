package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.model.domain.User;

class SearchUserServiceProxyTest {
    private final String DEFAULT_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user = new User("Test", "User", DEFAULT_IMAGE_URL);
    private final User user2 = new User("Morgan", "Davis", "Momo", DEFAULT_IMAGE_URL);


    private SearchUserServiceProxy serviceProxySpy;

    @BeforeEach
    void setup(){
        serviceProxySpy = new SearchUserServiceProxy();
    }

    @Test
    void test_getUserHandler(){
        SearchRequest request = new SearchRequest(user.getAlias(), null);
        SearchResponse response = null;
        try{
            response = serviceProxySpy.getUser(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertEquals(user, response.getUser());
        Assertions.assertFalse(response.isFollowing());

    }

    @Test
    void test_getUserHandler_loggedin(){
        SearchRequest request = new SearchRequest(user2.getAlias(), user);
        SearchResponse response = null;
        try{
            response = serviceProxySpy.getUser(request);
        } catch (IOException e){
            System.out.println(e);
        }

        Assertions.assertNotNull(response);
        Assertions.assertEquals(user2, response.getUser());
        Assertions.assertTrue(response.isFollowing());
    }
}