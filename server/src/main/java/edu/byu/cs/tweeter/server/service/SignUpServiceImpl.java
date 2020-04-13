package edu.byu.cs.tweeter.server.service;


import edu.byu.cs.tweeter.model.service.SignUpService;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.server.dao.signUp.SignUpDAO;
import edu.byu.cs.tweeter.server.dao.signUp.SignUpDAOImpl;

public class SignUpServiceImpl implements SignUpService {
    @Override
    public SignUpResponse postSignUp(SignUpRequest request){
        SignUpDAO dao = new SignUpDAOImpl();
        return dao.postSignUp(request);
    }

}
