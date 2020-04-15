package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;


class StatusDAOTest {
    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", ""); // 2 followees
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", ""); // 6 followees
    private final User user6 = new User("Mother", "Teresa", ""); // 7 followees
    private final User user7 = new User("Harriett", "Hansen", "");
    private final User user8 = new User("Zoe", "Zabriski", "");
    private final User user9 = new User("Albert", "Awesome", ""); // 1  followee
    private final User user10 = new User("Star", "Student", "");
    private final User user11 = new User("Bo", "Bungle", "");
    private final User user12 = new User("Susie", "Sampson", "");

    private final List<User> users = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11);

    private final Status status1 = new Status(user2, "Testing status 1");
    private final Status status2 = new Status(user2, "Testing status 2 @FredFlintstone");
    private final Status status3 = new Status(user3, "Testing status 3");
    private final Status status4 = new Status(user4, "Testing status 4");
    private final Status status5 = new Status(user5, "Testing status 5");
    private final Status status6 = new Status(user6, "Testing status 6");
    private final Status status7 = new Status(user9, "Testing status 7");
    private final Status status8 = new Status(user1, "Testing status 8");
    private final Status status9 = new Status(user8, "Testing status 9");
    private final Status status10 = new Status(user8, "Testing status 10");

    private final List<Status> statuses = Arrays.asList(status1, status2, status3, status4, status5,
            status6, status7, status8, status9, status10);

    private StatusDAOMock statusDAOSpy;


    @BeforeEach
    void setup(){
        statusDAOSpy = Mockito.spy(new StatusDAOMock());

        UserGenerator mockUserGenerator = Mockito.mock(UserGenerator.class);
        Mockito.when(mockUserGenerator.generateUsers(Mockito.anyInt())).thenReturn(users);


        StatusGenerator mockStatusGenerator = Mockito.mock(StatusGenerator.class);
        Mockito.when(mockStatusGenerator.generateAllStatuses( Mockito.anyListOf(User.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(statuses);

        Mockito.when(statusDAOSpy.getStatusGenerator()).thenReturn(mockStatusGenerator);

        Mockito.when(statusDAOSpy.getUserGenerator()).thenReturn(mockUserGenerator);
    }

    @Test
    void testPostStatus(){
        StatusRequest request = new StatusRequest(user1, status1);
        StatusResponse response = statusDAOSpy.postStatus(request);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.success);
        Assertions.assertEquals(response.getMessage(), "Status posted");
    }

    @Test
    void testPostStatus_withMention(){
        StatusRequest request = new StatusRequest(user3, status2);
        StatusResponse response = statusDAOSpy.postStatus(request);

        Assertions.assertEquals(response.getMessage(), "Status posted");
    }

    @Test
    void testPostStatus_invalidUser(){
        StatusRequest request = new StatusRequest(user12, status1);
        StatusResponse response = statusDAOSpy.postStatus(request);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.success);
        Assertions.assertEquals(response.getMessage(), "[Bad Request]: Status cannot be posted by a user that does not exist");
    }

    @Test
    void testPostStatus_duplicateStatuses(){
        StatusRequest request = new StatusRequest(user3, status3);
        StatusResponse response = statusDAOSpy.postStatus(request);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.success);
        Assertions.assertEquals(response.getMessage(), "[Bad Request]: User has already posted that status.");
    }


}