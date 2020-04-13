package edu.byu.cs.tweeter.server.dao.signOut;

import edu.byu.cs.tweeter.model.service.request.SignOutRequest;

public interface SignOutDAO {
    void signOut(SignOutRequest request);
}
