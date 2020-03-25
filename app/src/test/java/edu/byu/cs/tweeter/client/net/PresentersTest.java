//package edu.byu.cs.tweeter.client.net;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.Arrays;
//import java.util.List;
//
//
//import edu.byu.cs.tweeter.test1.domain.Status;
//import edu.byu.cs.tweeter.test1.service.request.FeedRequest;
//import edu.byu.cs.tweeter.test1.service.request.FollowRequest;
//import edu.byu.cs.tweeter.test1.service.request.FollowerRequest;
//import edu.byu.cs.tweeter.test1.service.request.FollowingRequest;
//import edu.byu.cs.tweeter.test1.service.request.SearchRequest;
//import edu.byu.cs.tweeter.test1.service.request.SignInRequest;
//import edu.byu.cs.tweeter.test1.service.request.SignUpRequest;
//import edu.byu.cs.tweeter.test1.service.request.StatusRequest;
//import edu.byu.cs.tweeter.test1.service.request.StoryRequest;
//import edu.byu.cs.tweeter.test1.service.response.FeedResponse;
//import edu.byu.cs.tweeter.test1.service.response.FollowResponse;
//import edu.byu.cs.tweeter.test1.service.response.FollowerResponse;
//import edu.byu.cs.tweeter.test1.service.response.FollowingResponse;
//import edu.byu.cs.tweeter.test1.service.response.SearchResponse;
//import edu.byu.cs.tweeter.test1.service.response.SignInResponse;
//import edu.byu.cs.tweeter.test1.service.response.SignUpResponse;
//import edu.byu.cs.tweeter.test1.service.response.StatusResponse;
//import edu.byu.cs.tweeter.test1.service.response.StoryResponse;
//import edu.byu.cs.tweeter.client.presenter.feed.AbstractFeedPresenter;
//import edu.byu.cs.tweeter.client.presenter.feed.MockFeedPresenter;
//import edu.byu.cs.tweeter.client.presenter.follow.AbstractFollowPresenter;
//import edu.byu.cs.tweeter.client.presenter.follow.MockFollowPresenter;
//import edu.byu.cs.tweeter.client.presenter.follower.AbstractFollowerPresenter;
//import edu.byu.cs.tweeter.client.presenter.follower.MockFollowerPresenter;
//import edu.byu.cs.tweeter.client.presenter.following.AbstractFollowingPresenter;
//import edu.byu.cs.tweeter.client.presenter.following.MockFollowingPresenter;
//import edu.byu.cs.tweeter.client.presenter.main.AbstractMainPresenter;
//import edu.byu.cs.tweeter.client.presenter.search.AbstractSearchPresenter;
//import edu.byu.cs.tweeter.client.presenter.search.MockSearchPresenter;
//import edu.byu.cs.tweeter.client.presenter.signIn.AbstractSignInPresenter;
//import edu.byu.cs.tweeter.client.presenter.signIn.MockSignInPresenter;
//import edu.byu.cs.tweeter.client.presenter.signUp.AbstractSignUpPresenter;
//import edu.byu.cs.tweeter.client.presenter.signUp.MockSignUpPresenter;
//import edu.byu.cs.tweeter.client.presenter.status.AbstractStatusPresenter;
//import edu.byu.cs.tweeter.client.presenter.status.MockStatusPresenter;
//import edu.byu.cs.tweeter.client.presenter.story.AbstractStoryPresenter;
//import edu.byu.cs.tweeter.client.presenter.story.MockStoryPresenter;
//
//import edu.byu.cs.tweeter.client.presenter.main.MockMainPresenter;
//import edu.byu.cs.tweeter.client.view.main.MainActivity;
//import edu.byu.cs.tweeter.client.view.main.feed.FeedFragment;
//import edu.byu.cs.tweeter.client.view.main.follow.FollowFragment;
//import edu.byu.cs.tweeter.client.view.main.follower.FollowerFragment;
//import edu.byu.cs.tweeter.client.view.main.following.FollowingFragment;
//import edu.byu.cs.tweeter.client.view.main.search.SearchActivity;
//import edu.byu.cs.tweeter.client.view.main.signIn.SignInFragment;
//import edu.byu.cs.tweeter.client.view.main.signUp.SignUpFragment;
//import edu.byu.cs.tweeter.client.view.main.status.StatusActivity;
//import edu.byu.cs.tweeter.client.view.main.story.StoryFragment;
//
//class PresentersTest {
//
//
//    private final User user1 = new User("Daffy", "Duck", "");
//    private final User user2 = new User("Fred", "Flintstone", "");
//    private final User user3 = new User("Barney", "Rubble", ""); // 2 followees
//    private final User user4 = new User("Wilma", "Rubble", "");
//    private final User user5 = new User("Clint", "Eastwood", ""); // 6 followees
//    private final User user6 = new User("Mother", "Teresa", ""); // 7 followees
//    private final User user7 = new User("Harriett", "Hansen", "");
//    private final User user8 = new User("Zoe", "Zabriski", "");
//    private final User user9 = new User("Albert", "Awesome", ""); // 1  followee
//    private final User user10 = new User("Star", "Student", "");
//    private final User user11 = new User("Bo", "Bungle", "");
//    private final User user12 = new User("Susie", "Sampson", "");
//    private final User user13 = new User("John", "Doe", "");
//    private final User user14 = new User("Jane", "Doe", "");
//    private final User user15 = new User("Scooby", "Do", "");
//    private final User user16 = new User("Shawn", "Spencer", "");
//    private final User user17 = new User("Henry", "Spencer", "");
//    private final User testUser = new User("Test", "User", "");
//
//
//    private final Follow follow1 = new Follow(user9, user5);
//
//    private final Follow follow2 = new Follow(user3, user1);
//    private final Follow follow3 = new Follow(user3, user8);
//
//    private final Follow follow4 = new Follow(user5,  user9);
//    private final Follow follow5 = new Follow(user5,  user11);
//    private final Follow follow6 = new Follow(user5,  user1);
//    private final Follow follow7 = new Follow(user5,  user2);
//    private final Follow follow8 = new Follow(user5,  user4);
//    private final Follow follow9 = new Follow(user5,  user8);
//
//    private final Follow follow10 = new Follow(user6,  user3);
//    private final Follow follow11 = new Follow(user6,  user5);
//    private final Follow follow12 = new Follow(user6,  user1);
//    private final Follow follow13 = new Follow(user6,  user7);
//    private final Follow follow14 = new Follow(user6,  user10);
//    private final Follow follow15 = new Follow(user6,  user12);
//    private final Follow follow16 = new Follow(user6,  user4);
//
//    private final Follow follow17 = new Follow(user14,  user8);
//    private final Follow follow18 = new Follow(user15, user8);
//    private final Follow follow19 = new Follow(user13, user12);
//    private final Follow follow20 = new Follow(user16, user17);
//    private final Follow follow21 = new Follow(testUser, user1);
//
//    private final List<Follow> follows = Arrays.asList(follow1, follow2, follow3, follow4, follow5, follow6,
//            follow7, follow8, follow9, follow10, follow11, follow12, follow13, follow14, follow15,
//            follow16, follow17, follow18, follow19, follow20, follow21);
//
//    private final Status status1 = new Status(user3, "Testing status 1");
//    private final Status status2 = new Status(user3, "Testing status 2");
//    private final Status status3 = new Status(user3, "Testing status 3");
//    private final Status status4 = new Status(user5, "Testing status 4");
//    private final Status status5 = new Status(user5, "Testing status 5");
//    private final Status status6 = new Status(user9, "Testing status 6");
//    private final Status status7 = new Status(user9, "Testing status 7");
//    private final Status status8 = new Status(user1, "Testing status 8");
//    private final Status status9 = new Status(user8, "Testing status 9");
//    private final Status status10 = new Status(user8, "Testing status 10");
//
//    private final List<Status> statuses = Arrays.asList(status1, status2, status3, status4, status5,
//            status6, status7, status8, status9, status10);
//
//
//
//    private ServerFacade serverFacadeSpy;
//
//    private AbstractMainPresenter mainPresenterSpy;
//    private AbstractSignInPresenter signInPresenterSpy;
//
//    private AbstractFeedPresenter feedPresenterSpy;
//    private AbstractStoryPresenter storyPresenterSpy;
//    private AbstractFollowerPresenter followerPresenterSpy;
//    private AbstractFollowingPresenter followingPresenterSpy;
//    private AbstractFollowPresenter followPresenterSpy;
//    private AbstractStatusPresenter statusPresenterSpy;
//    private AbstractSearchPresenter searchPresenterSpy;
//    private AbstractSignUpPresenter signUpPresenterSpy;
//
//
//
//
//
//
//    @BeforeEach
//    void setup() {
//
//        serverFacadeSpy = Mockito.spy(new ServerFacade());
//
//        feedPresenterSpy = Mockito.spy(new MockFeedPresenter(new FeedFragment(), serverFacadeSpy));
//        mainPresenterSpy = Mockito.spy(new MockMainPresenter(new MainActivity(), serverFacadeSpy));
//        signInPresenterSpy = Mockito.spy(new MockSignInPresenter(new SignInFragment(), serverFacadeSpy));
//        storyPresenterSpy = Mockito.spy(new MockStoryPresenter(new StoryFragment(), serverFacadeSpy));
//        followerPresenterSpy = Mockito.spy(new MockFollowerPresenter(new FollowerFragment(), serverFacadeSpy));
//        followingPresenterSpy = Mockito.spy(new MockFollowingPresenter(new FollowingFragment(), serverFacadeSpy));
//        followPresenterSpy = Mockito.spy(new MockFollowPresenter(new FollowFragment(), serverFacadeSpy));
//        statusPresenterSpy = Mockito.spy(new MockStatusPresenter(new StatusActivity(), serverFacadeSpy));
//        searchPresenterSpy = Mockito.spy(new MockSearchPresenter(new SearchActivity(), serverFacadeSpy));
//        signUpPresenterSpy = Mockito.spy(new MockSignUpPresenter(new SignUpFragment(), serverFacadeSpy));
//
//
//        FollowGenerator mockFollowGenerator = Mockito.mock(FollowGenerator.class);
//        Mockito.when(mockFollowGenerator.generateUsersAndFollows(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), (FollowGenerator.Sort) Mockito.any())).thenReturn(follows);
//
//        Mockito.when(serverFacadeSpy.getFollowGenerator()).thenReturn(mockFollowGenerator);
//
//        StatusGenerator mockStatusGenerator = Mockito.mock(StatusGenerator.class);
//        Mockito.when(mockStatusGenerator.generateAllStatuses((List<User>) Mockito.any(),  Mockito.anyInt(), Mockito.anyInt())).thenReturn(statuses);
//
//        Mockito.when(serverFacadeSpy.getStatusGenerator()).thenReturn(mockStatusGenerator);
//    }
//
//    /**
//     * -----------------------------------------------Testing Main Presenter --------------------------
//     */
//
//    @Test
//    void signOutCurrentUser(){
//        SignInRequest request = new SignInRequest("@TestUser", "Password");
//        SignInResponse response = signInPresenterSpy.postSignIn(request);
//
//        Assertions.assertEquals(testUser, signInPresenterSpy.getCurrentUser());
//
//        mainPresenterSpy.signOutUser();
//        Assertions.assertNull(mainPresenterSpy.getCurrentUser());
//    }
//
//
//    /**
//     * -----------------------------------------------Testing Sign in  Presenter --------------------------
//     */
//
//    @Test
//    void testSignIn_validUser(){
//
//        SignInRequest request = new SignInRequest("@TestUser", "Password");
//        SignInResponse response = signInPresenterSpy.postSignIn(request);
//
//        Assertions.assertNotNull(response.getUser());
//        Assertions.assertEquals("Test User", response.getUser().getName());
//        Assertions.assertEquals("@TestUser", response.getUser().getAlias());
//
//        Assertions.assertEquals(testUser, signInPresenterSpy.getCurrentUser());
//    }
//
//    @Test
//    void testSignIn_validUser_WrongPassword(){
//        SignInRequest request = new SignInRequest("@TestUser", "random123");
//        SignInResponse response = signInPresenterSpy.postSignIn(request);
//
//        Assertions.assertNull(response.getUser());
//        Assertions.assertEquals("Invalid alias or password", response.getMessage());
//    }
//
//    @Test
//    void testSignIn_nonexistingUser(){
//        SignInRequest request = new SignInRequest("@RandomUser", "Password");
//        SignInResponse response = signInPresenterSpy.postSignIn(request);
//
//        Assertions.assertNull(response.getUser());
//        Assertions.assertEquals("User with given alias (@RandomUser) does not exist.", response.getMessage());
//    }
//
//    /*--------------------------------- Signup test---------------------------------------*/
//
//    @Test
//    void testPostUser_DuplicateAlias(){
//        User user = new User("Daffy", "Duck", "");
//        String password = "password123";
//        SignUpRequest request = new SignUpRequest(user, password, "" );
//        SignUpResponse response = signUpPresenterSpy.postUser(request);
//
//        Assertions.assertEquals(response.getMessage(), "Alias is already taken");
//        Assertions.assertNull(response.getUser());
//    }
//
//    @Test
//    void testPostUser_sameName_differentAlias(){
//        User user = new User("Daffy", "Duck", "@ducky", "");
//        String password = "password123";
//        SignUpRequest request = new SignUpRequest(user, password, "");
//        SignUpResponse response = signUpPresenterSpy.postUser(request);
//
//        Assertions.assertEquals(response.getUser(), user);
//        Assertions.assertNotEquals( "Alias is already taken", response.getMessage());
//    }
//
//    @Test
//    void testPostUser_validUsername(){
//        User user = new User("Morgan", "Davis", "@Morgan", "");
//        String password = "password123";
//        SignUpRequest request = new SignUpRequest(user, password, "");
//        SignUpResponse response = signUpPresenterSpy.postUser(request);
//
//        Assertions.assertEquals(response.getUser(), user);
//        Assertions.assertNotEquals( "Alias is already taken", response.getMessage());
//    }
//
//
//    @Test
//    void testPostUser_checkStory(){
//        User user = new User("Jacob", "Davis", "@Jacob", "");
//        String password = "password123";
//        SignUpRequest request = new SignUpRequest(user, password, "");
//        SignUpResponse response = signUpPresenterSpy.postUser(request);
//
//        Assertions.assertEquals(response.getUser(), user);
//        Assertions.assertNotEquals( "Alias is already taken", response.getMessage());
//
//        StoryRequest storyRequest = new StoryRequest(user, 5, null);
//        StoryResponse storyResponse = storyPresenterSpy.getStory(storyRequest);
//
//        Assertions.assertEquals(0, storyResponse.getStory().getStory().size());
//    }
//
//    @Test
//    void testPostUser_checkFeed(){
//        User user = new User("Morgan", "Davis", "");
//        String password = "password123";
//        SignUpRequest request = new SignUpRequest(user, password, "");
//        SignUpResponse response = signUpPresenterSpy.postUser(request);
//
//        Assertions.assertEquals(response.getUser(), user);
//        Assertions.assertNotEquals( "Alias is already taken", response.getMessage());
//
//        FeedRequest feedRequest = new FeedRequest(user, 5, null);
//        FeedResponse feedResponse = feedPresenterSpy.getFeed(feedRequest);
//
//        Assertions.assertEquals(0, feedResponse.getFeed().getFeed().size());
//    }
//
//    /**
//     * -----------------------------------------------Feed Presenter --------------------------
//     */
//
//    @Test
//    void testGetFeed_noFollowees_emptyFeed(){
//        FeedRequest request = new FeedRequest(user1, 2, null);
//        FeedResponse response = feedPresenterSpy.getFeed(request);
//
//        Assertions.assertEquals(0, response.getFeed().getFeed().size());
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//    @Test
//    void testGetFeed_hasFolloweesButNoStatuses_emptyFeed(){
//        FeedRequest request = new FeedRequest(user13, 2, null);
//        FeedResponse response = feedPresenterSpy.getFeed(request);
//
//        Assertions.assertEquals(0, response.getFeed().getFeed().size());
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//    @Test
//    void testGetFeed_limitLessThanStatuses(){
//        FeedRequest request = new FeedRequest(user3, 2, null);
//        FeedResponse response = feedPresenterSpy.getFeed(request);
//        // Verify first page
//        Assertions.assertEquals(2, response.getFeed().getFeed().size());
//        Assertions.assertNotEquals(response.getFeed().getFeed().get(0).getAuthor(),user3);
//        Assertions.assertNotEquals(response.getFeed().getFeed().get(0).getAuthor(),user3);
//        Assertions.assertEquals(response.getFeed().getFeed().get(0).getAuthor(),user1);
//        Assertions.assertEquals(response.getFeed().getFeed().get(1).getAuthor(),user8);
//        Assertions.assertTrue(response.hasMorePages());
//
//        Status last_status = response.getFeed().getFeed().get(1);
//
//        // Verify second page
//        request = new FeedRequest(user3, 2, last_status);
//        response = feedPresenterSpy.getFeed(request);
//
//        Assertions.assertEquals(1, response.getFeed().getFeed().size());
//        Assertions.assertNotEquals(response.getFeed().getFeed().get(0).getAuthor(),user3);
//        Assertions.assertEquals(response.getFeed().getFeed().get(0).getAuthor(),user8);
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//    /**
//     * -------------------------------------------Story Presenter --------------------------
//     */
//    @Test
//    void testGetStory_noStatusesForUser(){
//        StoryRequest request = new StoryRequest(user14, 2, null);
//        StoryResponse response = storyPresenterSpy.getStory(request);
//
//        Assertions.assertEquals(0, response.getStory().getStory().size());
//        Assertions.assertFalse(response.hasMorePages());
//
//    }
//
//
//    @Test
//    void testGetStory_limitLessThanStatuses(){
//        StoryRequest request = new StoryRequest(user3, 2, null);
//        StoryResponse response = storyPresenterSpy.getStory(request);
//
//        // Verify first page
//        Assertions.assertEquals(2, response.getStory().getStory().size());
//        Assertions.assertEquals(response.getStory().getStory().get(0).getAuthor(),user3);
//        Assertions.assertTrue(response.hasMorePages());
//
//        Status last_status = response.getStory().getStory().get(1);
//
//        // Verify second page
//        request = new StoryRequest(user3, 2, last_status);
//        response = storyPresenterSpy.getStory(request);
//
//        Assertions.assertEquals(1, response.getStory().getStory().size());
//        Assertions.assertEquals(response.getStory().getStory().get(0).getAuthor(),user3);
//        Assertions.assertNotEquals(last_status, response.getStory().getStory().get(0));
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//
//
//    @Test
//    void testGetStory_limitMoreThanStatuses(){
//        StoryRequest request = new StoryRequest(user3, 5, null);
//        StoryResponse response = serverFacadeSpy.getStory(request);
//
//        // Verify first page
//        Assertions.assertEquals(3, response.getStory().getStory().size());
//        Assertions.assertEquals(response.getStory().getStory().get(0).getAuthor(),user3);
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//
//
//    /**
//     * ------------------------------Follower Presenter --------------------------
//     */
//
//    /*No one is following the user*/
//    @Test
//    void testGetFollowers_noFollowersForUser() {
//
//        FollowerRequest request = new FollowerRequest(user6, 10, null);
//        FollowerResponse response = followerPresenterSpy.getFollowers(request);
//
//        Assertions.assertEquals(0, response.getFollowers().size());
//        Assertions.assertFalse(response.hasMorePages());
//
//    }
//
//
//    @Test
//    void testGetFollowers_oneFolloweeForUser_limitGreaterThanUsers() {
//        FollowerRequest request = new FollowerRequest(user2, 10, null);
//        FollowerResponse response = followerPresenterSpy.getFollowers(request);
//
//        Assertions.assertEquals(1, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user5));
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//    @Test
//    void testGetFollowers_twoFolloweesForUser_limitEqualsUsers() {
//
//        FollowerRequest request = new FollowerRequest(user4, 2, null);
//        FollowerResponse response = followerPresenterSpy.getFollowers(request);
//
//        Assertions.assertEquals(2, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user5));
//        Assertions.assertTrue(response.getFollowers().contains(user6));
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//    @Test
//    void testGetFollowers_limitLessThanUsers_endsOnPageBoundary() {
//
//        FollowerRequest request = new FollowerRequest(user8, 2, null);
//        FollowerResponse response = followerPresenterSpy.getFollowers(request);
//
//        // Verify first page
//        Assertions.assertEquals(2, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user3));
//        Assertions.assertTrue(response.getFollowers().contains(user5));
//        Assertions.assertTrue(response.hasMorePages());
//
//        // Get and verify second page
//        request = new FollowerRequest(user8, 2, response.getFollowers().get(1));
//        response = followerPresenterSpy.getFollowers(request);
//
//        Assertions.assertEquals(2, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user14));
//        Assertions.assertTrue(response.getFollowers().contains(user15));
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//    /**
//     * ------------------------------Following Presenter --------------------------
//     */
//
//    /*User isn't following anyone */
//    @Test
//    void testGetFollowees_noFolloweesForUser() {
//
//        FollowingRequest request = new FollowingRequest(user1, 10, null);
//        FollowingResponse response = followingPresenterSpy.getFollowing(request);
//
//        Assertions.assertEquals(0, response.getFollowees().size());
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//    @Test
//    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() {
//
//        FollowingRequest request = new FollowingRequest(user9, 10, null);
//        FollowingResponse response = followingPresenterSpy.getFollowing(request);
//
//        Assertions.assertEquals(1, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user5));
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//    @Test
//    void testGetFollowees_twoFollowersForUser_limitEqualsUsers() {
//
//        FollowingRequest request = new FollowingRequest(user3, 2, null);
//        FollowingResponse response = followingPresenterSpy.getFollowing(request);
//
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user1));
//        Assertions.assertTrue(response.getFollowees().contains(user8));
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//    @Test
//    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {
//
//        FollowingRequest request = new FollowingRequest(user5, 2, null);
//        FollowingResponse response = followingPresenterSpy.getFollowing(request);
//
//        // Verify first page
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user9));
//        Assertions.assertTrue(response.getFollowees().contains(user11));
//        Assertions.assertTrue(response.hasMorePages());
//
//        // Get and verify second page
//        request = new FollowingRequest(user5, 2, response.getFollowees().get(1));
//        response = followingPresenterSpy.getFollowing(request);
//
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user1));
//        Assertions.assertTrue(response.getFollowees().contains(user2));
//        Assertions.assertTrue(response.hasMorePages());
//
//        // Get and verify third page
//        request = new FollowingRequest(user5, 2, response.getFollowees().get(1));
//        response = followingPresenterSpy.getFollowing(request);
//
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user4));
//        Assertions.assertTrue(response.getFollowees().contains(user8));
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//
//    @Test
//    void testGetFollowees_limitLessThanUsers_notEndsOnPageBoundary() {
//
//        FollowingRequest request = new FollowingRequest(user6, 2, null);
//        FollowingResponse response = followingPresenterSpy.getFollowing(request);
//
//        // Verify first page
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user3));
//        Assertions.assertTrue(response.getFollowees().contains(user5));
//        Assertions.assertTrue(response.hasMorePages());
//
//        // Get and verify second page
//        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
//        response = followingPresenterSpy.getFollowing(request);
//
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user1));
//        Assertions.assertTrue(response.getFollowees().contains(user7));
//        Assertions.assertTrue(response.hasMorePages());
//
//        // Get and verify third page
//        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
//        response =followingPresenterSpy.getFollowing(request);
//
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user10));
//        Assertions.assertTrue(response.getFollowees().contains(user12));
//        Assertions.assertTrue(response.hasMorePages());
//
//        // Get and verify fourth page
//        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
//        response = followingPresenterSpy.getFollowing(request);
//
//        Assertions.assertEquals(1, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user4));
//        Assertions.assertFalse(response.hasMorePages());
//    }
//
//
//
//    /*--------------------------------- Follow test---------------------------------------*/
//    @Test
//    void testPostFollow(){
//        FollowerRequest followerRequest = new FollowerRequest(user2, 5,null);
//        FollowerResponse followerResponse = followerPresenterSpy.getFollowers(followerRequest);
//        Assertions.assertFalse(followerResponse.getFollowers().contains(user1));
//
//        Follow follow = new Follow(user3, user2);
//        FollowRequest request = new FollowRequest(follow, true);
//        FollowResponse response = followPresenterSpy.postFollow(request);
//
//        Assertions.assertEquals("Follow posted", response.getMessage());
//
//        followerRequest = new FollowerRequest(user2, 5,null);
//        followerResponse = followerPresenterSpy.getFollowers(followerRequest);
//        Assertions.assertTrue(followerResponse.getFollowers().contains(user3));
//    }
//
//    @Test
//    void testPostFollow_followSelf(){
//        Follow follow = new Follow(user1, user1);
//        FollowRequest request = new FollowRequest(follow, true);
//        FollowResponse response = followPresenterSpy.postFollow(request);
//
//        Assertions.assertEquals("User can't follow or unfollow themself", response.getMessage());
//    }
//
//    @Test
//    void testPostFollow_nonexistingFollowee(){
//        User nonexisting = new User("No", "Name", "");
//        Follow follow = new Follow(user1, nonexisting);
//        FollowRequest request = new FollowRequest(follow, true);
//        FollowResponse response = followPresenterSpy.postFollow(request);
//
//        Assertions.assertEquals("Followee doesn't exist", response.getMessage());
//    }
//
//    @Test
//    void testPostFollow_nonexistingFollower(){
//        User nonexisting = new User("No", "Name", "");
//        Follow follow = new Follow(nonexisting, user1);
//        FollowRequest request = new FollowRequest(follow, true);
//        FollowResponse response = followPresenterSpy.postFollow(request);
//
//        Assertions.assertEquals("Follower doesn't exist", response.getMessage());
//    }
//
//    @Test
//    void testPostFollow_alreadyFollowing(){
//        Follow follow = new Follow(user9, user5);
//        FollowRequest request = new FollowRequest(follow, true);
//        FollowResponse response = followPresenterSpy.postFollow(request);
//
//        Assertions.assertEquals("Follow relationship already exists", response.getMessage());
//    }
//
//    /*---------------------------------Status test---------------------------------------*/
//    @Test
//    void testPostStatus(){
//        Status status = new Status(user1, "Testing posting status");
//        StatusRequest request = new StatusRequest(user1, status);
//        StatusResponse response = statusPresenterSpy.postStatus(request);
//
//        Assertions.assertEquals(response.getMessage(), "Status posted");
//
//        StoryRequest storyRequest = new StoryRequest(user1, 5, null);
//        StoryResponse storyResponse = storyPresenterSpy.getStory(storyRequest);
//
//        Assertions.assertEquals(2, storyResponse.getStory().getStory().size());
//        Assertions.assertTrue(storyResponse.getStory().getStory().contains(status));
//
//    }
//
//    @Test
//    void testPostStatus_withMention(){
//        Status status = new Status(user2, "Testing posting status @DaffyDuck");
//        StatusRequest request = new StatusRequest(user2, status);
//        StatusResponse response = statusPresenterSpy.postStatus(request);
//
//        Assertions.assertEquals(response.getMessage(), "Status posted");
//
//        StoryRequest storyRequest = new StoryRequest(user2, 5, null);
//        StoryResponse storyResponse = storyPresenterSpy.getStory(storyRequest);
//
//        Assertions.assertEquals(1, storyResponse.getStory().getStory().size());
//        Assertions.assertTrue(storyResponse.getStory().getStory().contains(status));
//        Status returned = storyResponse.getStory().getStory().get(0);
//        Assertions.assertEquals(1, returned.getMentions().getUserMentions().size());
//        Assertions.assertEquals("@DaffyDuck", returned.getMentions().getUserMentions().get(0));
//    }
//
//    @Test
//    void testPostStatus_withMentions(){
//        Status status = new Status(testUser, "Testing posting status @DaffyDuck @FredFlintstone");
//        StatusRequest request = new StatusRequest(testUser, status);
//        StatusResponse response = statusPresenterSpy.postStatus(request);
//
//        Assertions.assertEquals(response.getMessage(), "Status posted");
//
//        StoryRequest storyRequest = new StoryRequest(testUser, 5, null);
//        StoryResponse storyResponse = storyPresenterSpy.getStory(storyRequest);
//
//        Assertions.assertEquals(1, storyResponse.getStory().getStory().size());
//        Assertions.assertTrue(storyResponse.getStory().getStory().contains(status));
//        Assertions.assertEquals(2, storyResponse.getStory().getStory().get(0).getMentions().getUserMentions().size());
//
//    }
//
//    /*--------------------------------- Search user test---------------------------------------*/
//    @Test
//    void testSearchAlias_existingUser_following(){
//        String alias = "@DaffyDuck";
//        SearchRequest request = new SearchRequest(alias, user3);
//        SearchResponse response = searchPresenterSpy.searchAlias(request);
//
//        Assertions.assertEquals(String.format("Found user with given alias %s", alias), response.getMessage());
//        Assertions.assertEquals(response.getUser(), user1);
//
//        /*Check if the current user is following them*/
//        Assertions.assertTrue(response.isFollowing());
//    }
//
//    @Test
//    void testSearchAlias_existingUser_notFollowing(){
//        String alias = "@DaffyDuck";
//        SearchRequest request = new SearchRequest(alias, user2);
//        SearchResponse response = searchPresenterSpy.searchAlias(request);
//
//        Assertions.assertEquals(String.format("Found user with given alias %s", alias), response.getMessage());
//        Assertions.assertEquals(response.getUser(), user1);
//
//        /*Check if the current user is following them*/
//        Assertions.assertFalse(response.isFollowing());
//    }
//
//    @Test
//    void testSearchAlias_nonexistingUser(){
//        String alias = "@Ducky";
//        SearchRequest request = new SearchRequest(alias, user3);
//        SearchResponse response = searchPresenterSpy.searchAlias(request);
//
//        Assertions.assertEquals(String.format("Could not find user with given alias %s", alias), response.getMessage());
//    }
//
//}
