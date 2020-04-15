package edu.byu.cs.tweeter.server.service;


import edu.byu.cs.tweeter.model.service.SignUpService;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.server.dao.ImageDAO;
import edu.byu.cs.tweeter.server.dao.signUp.SignUpDAO;
import edu.byu.cs.tweeter.server.dao.signUp.SignUpDAOImpl;

public class SignUpServiceImpl implements SignUpService {
    private final String DEFAULT_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    @Override
    public SignUpResponse postSignUp(SignUpRequest request){

        /*Upload image to s3 and fetch url*/
        String imageUrl = DEFAULT_IMAGE_URL;
        if(!request.getImageString().equals("")){
            imageUrl = uploadImage(request.getImageString());
            System.out.println("Successfully uploaded image");
        }

        request.getUser().setImageUrl(imageUrl);

        SignUpDAO dao = new SignUpDAOImpl();
        SignUpResponse response = dao.postSignUp(request);

        /*Authorize user and return a token*/
        String token = getAuthorization(request.getUser().getAlias());
        response.setToken(token);

        return response;
    }


    private String getAuthorization(String alias){
        AuthorizationServiceImpl authorizationService = new AuthorizationServiceImpl();
        return authorizationService.getAuthorization(alias);
    }

    private String uploadImage(String imageString){
        ImageDAO imageDAO = new ImageDAO();
        return imageDAO.uploadImage(imageString);
    }

}
