package edu.byu.cs.tweeter.server.dao.follow;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public interface FollowDAO {

    FollowResponse postFollow(FollowRequest request);
}
