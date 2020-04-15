package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.SearchUserService;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.server.dao.follow.FollowDAOImpl;
import edu.byu.cs.tweeter.server.dao.user.UserDAO;
import edu.byu.cs.tweeter.server.dao.user.UserDAOImpl;
import edu.byu.cs.tweeter.server.dao.user.UserDAOMock;

/**
 * Contains the business logic for getting a user based on alias.
 */
public class SearchUserServiceImpl implements SearchUserService {
    @Override
    public SearchResponse getUser(SearchRequest request){
        UserDAO dao = new UserDAOImpl();
        SearchResponse response = dao.getUser(request);

        boolean isFollowing = false;

        if(null != request.getCurrentUser()){
            isFollowing = checkFollow(request.getCurrentUser().getAlias(), response.getUser().getAlias());
        }
        response.setFollowing(isFollowing);
        return response;
    }


    private boolean checkFollow(String userAlias, String otherAlias){
        FollowDAOImpl followDAO = new FollowDAOImpl();
        return followDAO.getFollow(userAlias, otherAlias);
    }
}
