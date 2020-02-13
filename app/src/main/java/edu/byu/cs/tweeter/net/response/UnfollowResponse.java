package edu.byu.cs.tweeter.net.response;

public class UnfollowResponse extends Response {
    public UnfollowResponse(boolean success, String message) {
        super(success, message);
    }
}
