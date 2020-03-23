package edu.byu.cs.tweeter.server.service;


import edu.byu.cs.tweeter.model.service.SignUpService;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.server.dao.SignUpDAO;

public class SignUpServiceImpl implements SignUpService {
    @Override
    public SignUpResponse postSignUp(SignUpRequest request){
        SignUpDAO dao = new SignUpDAO();
        return dao.postSignUp(request);
    }

}
