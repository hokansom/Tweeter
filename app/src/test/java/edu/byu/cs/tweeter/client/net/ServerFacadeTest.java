package edu.byu.cs.tweeter.client.net;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.User;

class ServerFacadeTest {

    private final User user1 = new User("Test", "User", "");


    private ServerFacade serverFacadeSpy;

    @BeforeEach
    void setup() {
        serverFacadeSpy = Mockito.spy(new ServerFacade());

    }


}