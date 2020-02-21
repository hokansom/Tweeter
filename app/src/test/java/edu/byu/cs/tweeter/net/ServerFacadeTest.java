package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.SearchRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.SearchResponse;
import edu.byu.cs.tweeter.net.response.SignUpResponse;
import edu.byu.cs.tweeter.net.response.StatusResponse;
import edu.byu.cs.tweeter.net.response.StoryResponse;

class ServerFacadeTest {

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
    private final User user13 = new User("John", "Doe", "");
    private final User user14 = new User("Jane", "Doe", "");
    private final User user15 = new User("Scooby", "Do", "");
    private final User user16 = new User("Shawn", "Spencer", "");
    private final User user17 = new User("Henry", "Spencer", "");


    private final Follow follow1 = new Follow(user9, user5);

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
    private final Follow follow15 = new Follow(user6,  user12);
    private final Follow follow16 = new Follow(user6,  user4);

    private final Follow follow17 = new Follow(user14,  user8);
    private final Follow follow18 = new Follow(user15, user8);
    private final Follow follow19 = new Follow(user13, user12);
    private final Follow follow20 = new Follow(user16, user17);


    private final List<Follow> follows = Arrays.asList(follow1, follow2, follow3, follow4, follow5, follow6,
            follow7, follow8, follow9, follow10, follow11, follow12, follow13, follow14, follow15,
            follow16, follow17, follow18, follow19, follow20);

    private final Status status1 = new Status(user3, "Testing status 1");
    private final Status status2 = new Status(user3, "Testing status 2");
    private final Status status3 = new Status(user3, "Testing status 3");
    private final Status status4 = new Status(user5, "Testing status 4");
    private final Status status5 = new Status(user5, "Testing status 5");
    private final Status status6 = new Status(user9, "Testing status 6");
    private final Status status7 = new Status(user9, "Testing status 7");
    private final Status status8 = new Status(user1, "Testing status 8");
    private final Status status9 = new Status(user8, "Testing status 9");
    private final Status status10 = new Status(user8, "Testing status 10");

    private final List<Status> statuses = Arrays.asList(status1, status2, status3, status4, status5,
            status6, status7, status8, status9, status10);



    private ServerFacade serverFacadeSpy;

    @BeforeEach
    void setup() {
        serverFacadeSpy = Mockito.spy(new ServerFacade());

        FollowGenerator mockFollowGenerator = Mockito.mock(FollowGenerator.class);
        Mockito.when(mockFollowGenerator.generateUsersAndFollows(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), (FollowGenerator.Sort) Mockito.any())).thenReturn(follows);

        Mockito.when(serverFacadeSpy.getFollowGenerator()).thenReturn(mockFollowGenerator);

        StatusGenerator mockStatusGenerator = Mockito.mock(StatusGenerator.class);
        Mockito.when(mockStatusGenerator.generateAllStatuses((List<User>) Mockito.any(),  Mockito.anyInt(), Mockito.anyInt())).thenReturn(statuses);

        Mockito.when(serverFacadeSpy.getStatusGenerator()).thenReturn(mockStatusGenerator);
    }

    /*---------------------------------Followees test---------------------------------------*/

    /*User isn't following anyone */
    @Test
    void testGetFollowees_noFolloweesForUser() {

        FollowingRequest request = new FollowingRequest(user1, 10, null);
        FollowingResponse response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(0, response.getFollowees().size());
        Assertions.assertFalse(response.hasMorePages());
    }

    @Test
    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() {

        FollowingRequest request = new FollowingRequest(user9, 10, null);
        FollowingResponse response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user5));
        Assertions.assertFalse(response.hasMorePages());
    }

    @Test
    void testGetFollowees_twoFollowersForUser_limitEqualsUsers() {

        FollowingRequest request = new FollowingRequest(user3, 2, null);
        FollowingResponse response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user1));
        Assertions.assertTrue(response.getFollowees().contains(user8));
        Assertions.assertFalse(response.hasMorePages());
    }

    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {

        FollowingRequest request = new FollowingRequest(user5, 2, null);
        FollowingResponse response = serverFacadeSpy.getFollowees(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user9));
        Assertions.assertTrue(response.getFollowees().contains(user11));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify second page
        request = new FollowingRequest(user5, 2, response.getFollowees().get(1));
        response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user1));
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify third page
        request = new FollowingRequest(user5, 2, response.getFollowees().get(1));
        response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user4));
        Assertions.assertTrue(response.getFollowees().contains(user8));
        Assertions.assertFalse(response.hasMorePages());
    }


    @Test
    void testGetFollowees_limitLessThanUsers_notEndsOnPageBoundary() {

        FollowingRequest request = new FollowingRequest(user6, 2, null);
        FollowingResponse response = serverFacadeSpy.getFollowees(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user3));
        Assertions.assertTrue(response.getFollowees().contains(user5));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify second page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user1));
        Assertions.assertTrue(response.getFollowees().contains(user7));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify third page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user10));
        Assertions.assertTrue(response.getFollowees().contains(user12));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify fourth page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user4));
        Assertions.assertFalse(response.hasMorePages());
    }

    /*---------------------------------Followers test---------------------------------------*/

    /*No one is following the user*/
    @Test
    void testGetFollowers_noFollowersForUser() {

        FollowerRequest request = new FollowerRequest(user6, 10, null);
        FollowerResponse response = serverFacadeSpy.getFollowers(request);

        Assertions.assertEquals(0, response.getFollowers().size());
        Assertions.assertFalse(response.hasMorePages());
    }


    @Test
    void testGetFollowers_oneFolloweeForUser_limitGreaterThanUsers() {
        FollowerRequest request = new FollowerRequest(user2, 10, null);
        FollowerResponse response = serverFacadeSpy.getFollowers(request);

        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user5));
        Assertions.assertFalse(response.hasMorePages());
    }

    @Test
    void testGetFollowers_twoFolloweesForUser_limitEqualsUsers() {

        FollowerRequest request = new FollowerRequest(user4, 2, null);
        FollowerResponse response = serverFacadeSpy.getFollowers(request);

        Assertions.assertEquals(2, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user5));
        Assertions.assertTrue(response.getFollowers().contains(user6));
        Assertions.assertFalse(response.hasMorePages());
    }

    @Test
    void testGetFollowers_limitLessThanUsers_endsOnPageBoundary() {

        FollowerRequest request = new FollowerRequest(user8, 2, null);
        FollowerResponse response = serverFacadeSpy.getFollowers(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user3));
        Assertions.assertTrue(response.getFollowers().contains(user5));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify second page
        request = new FollowerRequest(user8, 2, response.getFollowers().get(1));
        response = serverFacadeSpy.getFollowers(request);

        Assertions.assertEquals(2, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user14));
        Assertions.assertTrue(response.getFollowers().contains(user15));
        Assertions.assertFalse(response.hasMorePages());
    }

    /*--------------------------------- Follow test---------------------------------------*/
    @Test
    void testPostFollow(){
        FollowerRequest followerRequest = new FollowerRequest(user2, 5,null);
        FollowerResponse followerResponse = serverFacadeSpy.getFollowers(followerRequest);
        Assertions.assertFalse(followerResponse.getFollowers().contains(user1));

        Follow follow = new Follow(user3, user2);
        FollowRequest request = new FollowRequest(follow);
        FollowResponse response = serverFacadeSpy.postFollow(request);

        Assertions.assertEquals("Follow posted", response.getMessage());

        followerRequest = new FollowerRequest(user2, 5,null);
        followerResponse = serverFacadeSpy.getFollowers(followerRequest);
        Assertions.assertTrue(followerResponse.getFollowers().contains(user3));
    }

    @Test
    void testPostFollow_followSelf(){
        Follow follow = new Follow(user1, user1);
        FollowRequest request = new FollowRequest(follow);
        FollowResponse response = serverFacadeSpy.postFollow(request);

        Assertions.assertEquals("User can't follow themself", response.getMessage());
    }

    @Test
    void testPostFollow_nonexistingFollowee(){
        User nonexisting = new User("No", "Name", "");
        Follow follow = new Follow(user1, nonexisting);
        FollowRequest request = new FollowRequest(follow);
        FollowResponse response = serverFacadeSpy.postFollow(request);

        Assertions.assertEquals("Followee doesn't exist", response.getMessage());
    }

    @Test
    void testPostFollow_nonexistingFollower(){
        User nonexisting = new User("No", "Name", "");
        Follow follow = new Follow(nonexisting, user1);
        FollowRequest request = new FollowRequest(follow);
        FollowResponse response = serverFacadeSpy.postFollow(request);

        Assertions.assertEquals("Follower doesn't exist", response.getMessage());
    }

    @Test
    void testPostFollow_alreadyFollowing(){
        Follow follow = new Follow(user9, user5);
        FollowRequest request = new FollowRequest(follow);
        FollowResponse response = serverFacadeSpy.postFollow(request);

        Assertions.assertEquals("Follow relationship already exists", response.getMessage());
    }



    /*---------------------------------Stories test---------------------------------------*/

    @Test
    void testGetStory_noStatusesForUser(){
        StoryRequest request = new StoryRequest(user14, 2, null);
        StoryResponse response = serverFacadeSpy.getStory(request);

        Assertions.assertEquals(0, response.getStory().getStory().size());
        Assertions.assertFalse(response.hasMorePages());

    }


    @Test
    void testGetStory_limitLessThanStatuses(){
        StoryRequest request = new StoryRequest(user3, 2, null);
        StoryResponse response = serverFacadeSpy.getStory(request);

        // Verify first page
        Assertions.assertEquals(2, response.getStory().getStory().size());
        Assertions.assertTrue(response.getStory().getStory().get(0).getAuthor().equals(user3));
        Assertions.assertTrue(response.hasMorePages());

        Status last_status = response.getStory().getStory().get(1);

        // Verify second page
        request = new StoryRequest(user3, 2, last_status);
        response = serverFacadeSpy.getStory(request);

        Assertions.assertEquals(1, response.getStory().getStory().size());
        Assertions.assertTrue(response.getStory().getStory().get(0).getAuthor().equals(user3));
        Assertions.assertNotEquals(last_status, response.getStory().getStory().get(0));
        Assertions.assertFalse(response.hasMorePages());
    }



    @Test
    void testGetStory_limitMoreThanStatuses(){
        StoryRequest request = new StoryRequest(user3, 5, null);
        StoryResponse response = serverFacadeSpy.getStory(request);

        // Verify first page
        Assertions.assertEquals(3, response.getStory().getStory().size());
        Assertions.assertTrue(response.getStory().getStory().get(0).getAuthor().equals(user3));
        Assertions.assertFalse(response.hasMorePages());
    }



    /*---------------------------------Feeds test---------------------------------------*/

    @Test
    void testGetFeed_noFollowees_emptyFeed(){
        FeedRequest request = new FeedRequest(user1, 2, null);
        FeedResponse response = serverFacadeSpy.getFeed(request);

        Assertions.assertEquals(0, response.getFeed().getFeed().size());
        Assertions.assertFalse(response.hasMorePages());

    }

    @Test
    void testGetFeed_hasFolloweesButNoStatuses_emptyFeed(){
        FeedRequest request = new FeedRequest(user13, 2, null);
        FeedResponse response = serverFacadeSpy.getFeed(request);

        Assertions.assertEquals(0, response.getFeed().getFeed().size());
        Assertions.assertFalse(response.hasMorePages());

    }
    @Test
    void testGetFeed_limitLessThanStatuses(){
        FeedRequest request = new FeedRequest(user3, 2, null);
        FeedResponse response = serverFacadeSpy.getFeed(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFeed().getFeed().size());
        Assertions.assertNotEquals(response.getFeed().getFeed().get(0).getAuthor(),user3);
        Assertions.assertNotEquals(response.getFeed().getFeed().get(0).getAuthor(),user3);
        Assertions.assertEquals(response.getFeed().getFeed().get(0).getAuthor(),user1);
        Assertions.assertEquals(response.getFeed().getFeed().get(1).getAuthor(),user8);
        Assertions.assertTrue(response.hasMorePages());

        Status last_status = response.getFeed().getFeed().get(1);

        // Verify second page
        request = new FeedRequest(user3, 2, last_status);
        response = serverFacadeSpy.getFeed(request);

        Assertions.assertEquals(1, response.getFeed().getFeed().size());
        Assertions.assertNotEquals(response.getFeed().getFeed().get(0).getAuthor(),user3);
        Assertions.assertEquals(response.getFeed().getFeed().get(0).getAuthor(),user8);
        Assertions.assertFalse(response.hasMorePages());
    }

    /*---------------------------------Status test---------------------------------------*/
    @Test
    void testPostStatus(){
        Status status = new Status(user1, "Testing posting status");
        StatusRequest request = new StatusRequest(user1, status);
        StatusResponse response = serverFacadeSpy.postStatus(request);

        Assertions.assertEquals(response.getMessage(), "Status posted");

        StoryRequest storyRequest = new StoryRequest(user1, 5, null);
        StoryResponse storyResponse = serverFacadeSpy.getStory(storyRequest);

        Assertions.assertEquals(2, storyResponse.getStory().getStory().size());
        Assertions.assertTrue(storyResponse.getStory().getStory().contains(status));

    }

//    @Test
//    void testPostStatus_withMention(){
//        Status status = new Status(user3, "Testing posting status @DaffyDuck");
//        StatusRequest request = new StatusRequest(user3, status);
//        StatusResponse response = serverFacadeSpy.postStatus(request);
//
//        Assertions.assertEquals(response.getMessage(), "Status posted");
//
//        StoryRequest storyRequest = new StoryRequest(user3, 5, null);
//        StoryResponse storyResponse = serverFacadeSpy.getStory(storyRequest);
//
//        Assertions.assertEquals(2, storyResponse.getStory().getStory().size());
//        Assertions.assertTrue(storyResponse.getStory().getStory().contains(status));
//
//    }
//
//    @Test
//    void testPostStatus_withMentions(){
//        Status status = new Status(user2, "Testing posting status @DaffyDuck @FredFlintstone");
//        StatusRequest request = new StatusRequest(user2, status);
//        StatusResponse response = serverFacadeSpy.postStatus(request);
//
//        Assertions.assertEquals(response.getMessage(), "Status posted");
//
//        StoryRequest storyRequest = new StoryRequest(user2, 5, null);
//        StoryResponse storyResponse = serverFacadeSpy.getStory(storyRequest);
//
//        Assertions.assertEquals(2, storyResponse.getStory().getStory().size());
//        Assertions.assertTrue(storyResponse.getStory().getStory().contains(status));
//
//    }

    /*--------------------------------- Signup test---------------------------------------*/

    @Test
    void testPostUser_DuplicateAlias(){
        User user = new User("Daffy", "Duck", "");
        SignUpRequest request = new SignUpRequest(user);
        SignUpResponse response = serverFacadeSpy.postUser(request);

        Assertions.assertEquals(response.getMessage(), "Alias is already taken");
        Assertions.assertNull(response.getUser());
    }

    @Test
    void testPostUser_sameName_differentAlias(){
        User user = new User("Daffy", "Duck", "@ducky", "");
        SignUpRequest request = new SignUpRequest(user);
        SignUpResponse response = serverFacadeSpy.postUser(request);

        Assertions.assertEquals(response.getUser(), user);
        Assertions.assertNotEquals( "Alias is already taken", response.getMessage());
    }

    @Test
    void testPostUser_validUsername(){
        User user = new User("Morgan", "Davis", "@Morgan", "");
        SignUpRequest request = new SignUpRequest(user);
        SignUpResponse response = serverFacadeSpy.postUser(request);

        Assertions.assertEquals(response.getUser(), user);
        Assertions.assertNotEquals( "Alias is already taken", response.getMessage());
    }


    @Test
    void testPostUser_checkStory(){
        User user = new User("Jacob", "Davis", "@Jacob", "");
        SignUpRequest request = new SignUpRequest(user);
        SignUpResponse response = serverFacadeSpy.postUser(request);

        Assertions.assertEquals(response.getUser(), user);
        Assertions.assertNotEquals( "Alias is already taken", response.getMessage());

        StoryRequest storyRequest = new StoryRequest(user, 5, null);
        StoryResponse storyResponse = serverFacadeSpy.getStory(storyRequest);

        Assertions.assertEquals(0, storyResponse.getStory().getStory().size());
    }

    @Test
    void testPostUser_checkFeed(){
        User user = new User("Morgan", "Davis", "");
        SignUpRequest request = new SignUpRequest(user);
        SignUpResponse response = serverFacadeSpy.postUser(request);

        Assertions.assertEquals(response.getUser(), user);
        Assertions.assertNotEquals( "Alias is already taken", response.getMessage());

        FeedRequest feedRequest = new FeedRequest(user, 5, null);
        FeedResponse feedResponse = serverFacadeSpy.getFeed(feedRequest);

        Assertions.assertEquals(0, feedResponse.getFeed().getFeed().size());
    }

    /*--------------------------------- Search user test---------------------------------------*/
    @Test
    void testSearchAlias_existingUser(){
        String alias = "@DaffyDuck";
        SearchRequest request = new SearchRequest(alias);
        SearchResponse response = serverFacadeSpy.searchUser(request);

        Assertions.assertEquals(String.format("Found user with given alias %s", alias), response.getMessage());
        Assertions.assertEquals(response.getUser(), user1);
    }

    @Test
    void testSearchAlias_nonexistingUser(){
        String alias = "@Ducky";
        SearchRequest request = new SearchRequest(alias);
        SearchResponse response = serverFacadeSpy.searchUser(request);

        Assertions.assertEquals(String.format("Could not find user with given alias %s", alias), response.getMessage());
    }
}
