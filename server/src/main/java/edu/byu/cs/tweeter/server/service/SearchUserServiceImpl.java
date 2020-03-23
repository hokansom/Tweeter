package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.SearchUserService;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.server.dao.UserDAO;

/**
 * Contains the business logic for getting a user based on alias.
 */
public class SearchUserServiceImpl implements SearchUserService {
    @Override
    public SearchResponse getUser(SearchRequest request){
        UserDAO dao = new UserDAO();
        return dao.getUser(request);
    }
}
