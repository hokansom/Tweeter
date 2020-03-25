package edu.byu.cs.tweeter.server.service;


import edu.byu.cs.tweeter.model.service.SignUpService;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class SignUpServiceImpl implements SignUpService {
    @Override
    public SignUpResponse postSignUp(SignUpRequest request){
        UserDAO dao = new UserDAO();
        return dao.postSignUp(request);
    }

}
