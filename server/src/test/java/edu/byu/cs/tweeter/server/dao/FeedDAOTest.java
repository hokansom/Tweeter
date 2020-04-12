package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

class FeedDAOTest {
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

    private final Status status1 = new Status(user2, "Testing status 1");
    private final Status status2 = new Status(user2, "Testing status 2");
    private final Status status3 = new Status(user3, "Testing status 3");
    private final Status status4 = new Status(user4, "Testing status 4");
    private final Status status5 = new Status(user5, "Testing status 5");
    private final Status status6 = new Status(user6, "Testing status 6");
    private final Status status7 = new Status(user9, "Testing status 7");
    private final Status status8 = new Status(user1, "Testing status 8");
    private final Status status9 = new Status(user8, "Testing status 9");
    private final Status status10 = new Status(user8, "Testing status 10");

    private final Follow follow1 = new Follow(user9, user7);

    private final Follow follow2 = new Follow(user3, user1);
    private final Follow follow3 = new Follow(user3, user8);

    private final Follow follow4 = new Follow(user5,  user9);
    private final Follow follow5 = new Follow(user5,  user11);
    private final Follow follow6 = new Follow(user5,  user1);
    private final Follow follow7 = new Follow(user5,  user2);
    private final Follow follow8 = new Follow(user5,  user4);
    private final Follow follow9 = new Follow(user5,  user8);

    private final Follow follow10 = new Follow(user6,  user3);
    private final Follow follow11 = new Follow(user6,  user5);
    private final Follow follow12 = new Follow(user6,  user1);
    private final Follow follow13 = new Follow(user6,  user7);
    private final Follow follow14 = new Follow(user6,  user10);
    private final Follow follow15 = new Follow(user6,  user4);

    private final String token = "123456789";


    private final List<Follow> follows = Arrays.asList(follow1, follow2, follow3, follow4, follow5, follow6,
            follow7, follow8, follow9, follow10, follow11, follow12, follow13, follow14, follow15);

    private final List<Status> statuses = Arrays.asList(status1, status2, status3, status4, status5,
            status6, status7, status8, status9, status10);


    private FeedDAO feedDAOSpy;

    @BeforeEach
    void setup(){
        feedDAOSpy = Mockito.spy(new FeedDAO());

        StatusGenerator mockStatusGenerator = Mockito.mock(StatusGenerator.class);
        Mockito.when(mockStatusGenerator.generateAllStatuses(Mockito.anyListOf(User.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(statuses);

        Mockito.when(feedDAOSpy.getStatusGenerator()).thenReturn(mockStatusGenerator);

        FollowGenerator mockFollowGenerator = Mockito.mock(FollowGenerator.class);
        Mockito.when(mockFollowGenerator.generateUsersAndFollows(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenReturn(follows);

        Mockito.when(feedDAOSpy.getFollowGenerator()).thenReturn(mockFollowGenerator);
    }

    @Test
    void testGetFeed_noFollowees_emptyFeed(){
        FeedRequest request = new FeedRequest(user1, 2, null, token);
        FeedResponse response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(0, response.getFeed().getFeed().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFeed_hasFolloweesButNoStatuses_emptyFeed(){
        FeedRequest request = new FeedRequest(user9, 2, null, token);
        FeedResponse response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(0, response.getFeed().getFeed().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFeed_limitLessThanStatuses(){
        FeedRequest request = new FeedRequest(user3, 2, null, token);
        FeedResponse response = feedDAOSpy.getFeed(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFeed().getFeed().size());
        Assertions.assertNotEquals(response.getFeed().getFeed().get(0).getAuthor(),user3);
        Assertions.assertNotEquals(response.getFeed().getFeed().get(0).getAuthor(),user3);
        Assertions.assertEquals(response.getFeed().getFeed().get(0).getAuthor(),user1);
        Assertions.assertEquals(response.getFeed().getFeed().get(1).getAuthor(),user8);
        Assertions.assertTrue(response.getHasMorePages());

        Status last_status = response.getFeed().getFeed().get(1);

        // Verify second page
        request = new FeedRequest(user3, 2, last_status, token);
        response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(1, response.getFeed().getFeed().size());
        Assertions.assertNotEquals(response.getFeed().getFeed().get(0).getAuthor(),user3);
        Assertions.assertEquals(response.getFeed().getFeed().get(0).getAuthor(),user8);
        Assertions.assertFalse(response.getHasMorePages());
    }

}