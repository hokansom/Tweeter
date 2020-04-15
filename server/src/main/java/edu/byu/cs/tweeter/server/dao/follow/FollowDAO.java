package edu.byu.cs.tweeter.server.dao.follow;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public interface FollowDAO {

    FollowResponse postFollow(FollowRequest request);

    boolean getFollow(String followerAlias, String followeeAlias);

    void addFollowersBatch(List<User> followers, User followed);
}
