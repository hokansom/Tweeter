package edu.byu.cs.tweeter.client.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;


public class ServerFacade {
    private static final String SERVER_URL = "https://ikjts5ql4e.execute-api.us-west-2.amazonaws.com/Test";


    /*--------------------------------FOLLOWEE-----------------------------------------------------*/

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);
    }

    /*--------------------------------------FOLLOWER-----------------------------------------------------*/


    /**
     * Returns the users that are following the user specified in the request. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the followers.
     */
    public FollowerResponse getFollowers(FollowerRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, FollowerResponse.class);
    }

    /*------------------------------------------FOLLOW RELATED-------------------------------------*/

    /**
     * Updates a follow relationship. Uses information in
     * the request object to either create the follow relationship between the followee and follower,
     * or delete the follow relationship between the followee and follower.
     *
     * @param request contains the data required to fulfill the request.
     * @return success
     */
    public FollowResponse postFollow(FollowRequest request, String auth, String urlPath) throws IOException{
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth);
        return clientCommunicator.doPost(urlPath, request, headers, FollowResponse.class);
    }

    /*------------------------------------------STATUS------------------------------*/


    /**
     * Posts a new status to the database. Returns if the post succeeded or not
     *
     * @param request contains the data required to fulfill the request.
     * @return a success boolean
     *
     */
    public StatusResponse postStatus(StatusRequest request, String auth, String urlPath) throws IOException{
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth);
        return clientCommunicator.doPost(urlPath, request, headers, StatusResponse.class);
    }


    /*------------------------------------------FEED-------------------------------------*/

    /**
     * Returns the feed of the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the feed.
     */
    public FeedResponse getFeed(FeedRequest request, String urlPath) throws IOException{
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, FeedResponse.class);

    }

    /*------------------------------------------STORY-------------------------------------*/

    /**
     * Returns the feed of the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the story.
     */
    public StoryResponse getStory(StoryRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, StoryResponse.class);
    }


    /*------------------------------------------USER-RELATED-------------------------------------*/

    /**
     * Returns the user for the alias specified in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    public SignInResponse postSignIn(SignInRequest request, String urlPath) throws IOException{
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, SignInResponse.class);
    }

    /**
     *
     * Returns the new user object generated by the database using the specified information in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     *             */
    public SignUpResponse postSignUp(SignUpRequest request, String urlPath) throws IOException{
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, SignUpResponse.class);
    }

    /**
     * Signs out the current user
     *
     * @param urlPath path for the api endpoint
     *
     * */
    public void signOut(String urlPath) throws IOException{
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        clientCommunicator.doGet(urlPath, null, null);
    }

    /**
     * Returns the user with the given alias specified in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user.
     */
    public SearchResponse getUser(SearchRequest request, String urlPath) throws IOException{
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath,request,null, SearchResponse.class);
    }

}

