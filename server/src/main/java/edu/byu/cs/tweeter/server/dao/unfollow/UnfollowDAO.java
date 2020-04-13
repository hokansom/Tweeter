package edu.byu.cs.tweeter.server.dao.unfollow;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public interface UnfollowDAO {
    FollowResponse deleteFollow(FollowRequest request);
}
