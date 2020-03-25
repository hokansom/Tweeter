package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

class StoryDAOTest {
    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", ""); // 2 followees
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", ""); // 6 followees


    private final Status status1 = new Status(user2, "Testing status 1");
    private final Status status2 = new Status(user3, "Testing status 2");
    private final Status status3 = new Status(user3, "Testing status 3");
    private final Status status4 = new Status(user4, "Testing status 4");
    private final Status status5 = new Status(user4, "Testing status 5");
    private final Status status6 = new Status(user4, "Testing status 6");
    private final Status status7 = new Status(user4, "Testing status 7");


    private final List<Status> statuses = Arrays.asList(status1, status2, status3, status4, status5,
            status6, status7);

    private StoryDAO storyDAOspy;

    @BeforeEach
    void setup(){
        storyDAOspy = Mockito.spy(new StoryDAO());

        StatusGenerator mockStatusGenerator = Mockito.mock(StatusGenerator.class);
        Mockito.when(mockStatusGenerator.generateAllStatuses(Mockito.anyListOf(User.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(statuses);

        Mockito.when(storyDAOspy.getStatusGenerator()).thenReturn(mockStatusGenerator);
    }

    @Test
    void testGetStory_noStatusesForUser(){
        StoryRequest request = new StoryRequest(user1, 2, null);
        StoryResponse response = storyDAOspy.getStory(request);

        Assertions.assertEquals(0, response.getStory().getStory().size());
        Assertions.assertFalse(response.success);
        Assertions.assertFalse(response.getHasMorePages());
    }



    @Test
    void testGetStory_limitMoreThanStatuses(){
        StoryRequest request = new StoryRequest(user2, 2, null);
        StoryResponse response = storyDAOspy.getStory(request);

        Assertions.assertEquals(1, response.getStory().getStory().size());
        Assertions.assertEquals(response.getStory().getStory().get(0).getAuthor(),user2);
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetStory_statusesEqualsLimit(){
        StoryRequest request = new StoryRequest(user3, 2, null);
        StoryResponse response = storyDAOspy.getStory(request);

        Assertions.assertEquals(2, response.getStory().getStory().size());
        Assertions.assertEquals(response.getStory().getStory().get(0).getAuthor(),user3);
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetStory_limitLessThanStatuses(){
        StoryRequest request = new StoryRequest(user4, 2, null);
        StoryResponse response = storyDAOspy.getStory(request);

        // Verify first page
        Assertions.assertEquals(2, response.getStory().getStory().size());
        Assertions.assertEquals(response.getStory().getStory().get(0).getAuthor(),user4);
        Assertions.assertTrue(response.getHasMorePages());

        Status last_status = response.getStory().getStory().get(1);

        // Verify second page
        request = new StoryRequest(user4, 2, last_status);
        response = storyDAOspy.getStory(request);

        Assertions.assertEquals(2, response.getStory().getStory().size());
        Assertions.assertEquals(response.getStory().getStory().get(0).getAuthor(),user4);
        Assertions.assertNotEquals(last_status, response.getStory().getStory().get(0));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetStory_invalidUser(){
        StoryRequest request = new StoryRequest(user5, 2, null);
        StoryResponse response = storyDAOspy.getStory(request);

        Assertions.assertFalse(response.success);
        Assertions.assertEquals(response.getMessage(), "Bad Request: User does not exist");
    }
}