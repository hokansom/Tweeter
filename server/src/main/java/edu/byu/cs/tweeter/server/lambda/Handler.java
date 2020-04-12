package edu.byu.cs.tweeter.server.lambda;

import edu.byu.cs.tweeter.server.service.AuthorizationServiceImpl;

public abstract class Handler {


    public Handler(){
    }

    protected void checkAuthorization(String alias, String token){
        AuthorizationServiceImpl authService = new AuthorizationServiceImpl();
        boolean isAuthorized = authService.checkAuthorization(alias, token);
        if(!isAuthorized){
            System.out.println("User is not authorized");
            throw new RuntimeException("[Unauthorized]: User has timed out due to inactivity");
        }
    }

    protected void checkForError(String message){
        if(!message.equals("")){
            System.out.println(message);
            throw new RuntimeException(message);
        }
    }

    protected void updateAuthTimestamp(String alias, String token){
        AuthorizationServiceImpl authService = new AuthorizationServiceImpl();
        authService.updateAuthorization(alias, token);
    }

    protected void forTestingValidActiveToken(String alias, String token){
        updateAuthTimestamp(alias, token);
    }
}
