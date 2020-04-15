package edu.byu.cs.tweeter.server.dao.user;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;

public interface UserDAO {
    /**
     * Gets the user from the database that has the specified alias from the request.
     *
     * @param request contains information about the user whose is being retrieved.
     * @return the user.
     */
   SearchResponse getUser(SearchRequest request);

   void addUserBatch(List<User> users);
}
