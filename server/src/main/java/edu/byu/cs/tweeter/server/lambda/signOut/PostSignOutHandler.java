package edu.byu.cs.tweeter.server.lambda.signOut;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.SignOutServiceImpl;

/**
 * An AWS lambda function that signs out the current user.
 */
public class PostSignOutHandler implements RequestHandler<Void, Void> {
    @Override
    public Void handleRequest(Void aVoid, Context context) {
        SignOutServiceImpl service = new SignOutServiceImpl();
        service.signOut();
        return null;
    }
}
