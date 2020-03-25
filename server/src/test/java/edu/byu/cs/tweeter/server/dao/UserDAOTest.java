package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.model.domain.User;

class UserDAOTest {

    private final User testUser = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);
    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", "");
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", "");
    private final User user6 = new User("Mother", "Teresa", "");

    private final List<User> users = Arrays.asList(testUser, user1, user2, user3, user4, user5);

    private final String password1 = "dc647eb65e6711e155375218212b3964";
    private final String password2 = "7f2ababa423061c509f4923dd04b6cf1";

    private Map<String, String> passwordsMap;

    private Map<String, String> auths;

    private UserDAO userDAOSpy;

    @BeforeEach
    void setup(){
        userDAOSpy = Mockito.spy(new UserDAO());

        UserGenerator mockUserGenerator = Mockito.mock(UserGenerator.class);
        Mockito.when(mockUserGenerator.generateUsers(Mockito.anyInt())).thenReturn(users);

        passwordsMap = buildTestPasswords();

        Mockito.when(mockUserGenerator.generatePasswords(Mockito.anyListOf(User.class))).thenReturn(passwordsMap);

        auths = buildTestAuths();
        Mockito.when(mockUserGenerator.generateTokens(Mockito.anyListOf(User.class))).thenReturn(auths);

        Mockito.when(userDAOSpy.getUserGenerator()).thenReturn(mockUserGenerator);

    }

    /*--------------------------------SIGN IN--------------------------------------------*/

    @Test
    void testSignIn_validUser(){
        SignInRequest request = new SignInRequest("@TestUser", "Password");
        SignInResponse response = userDAOSpy.postSignIn(request);

        Assertions.assertNotNull(response.getUser());
        Assertions.assertEquals("Test User", response.getUser().getName());
        Assertions.assertEquals("@TestUser", response.getUser().getAlias());
        Assertions.assertNotNull(response.getToken());
    }

    @Test
    void testSignIn_validUser_WrongPassword(){
        SignInRequest request = new SignInRequest("@TestUser", "random123");
        SignInResponse response = userDAOSpy.postSignIn(request);

        Assertions.assertNull(response.getUser());
        Assertions.assertEquals("[Bad Request]: Invalid alias or password", response.getMessage());
        Assertions.assertNull(response.getToken());
    }

    @Test
    void testSignIn_nonexistingUser(){
        SignInRequest request = new SignInRequest("@RandomUser", "Password");
        SignInResponse response = userDAOSpy.postSignIn(request);

        Assertions.assertNull(response.getUser());
        Assertions.assertEquals("[Bad Request]: User with given alias (@RandomUser) does not exist.", response.getMessage());
        Assertions.assertNull(response.getToken());
    }



    /*--------------------------------SIGN UP--------------------------------------------*/


    @Test
    void testSignUp_validUser(){
        String password = "password123";
        SignUpRequest request = new SignUpRequest(user6, password, "");
        SignUpResponse response = userDAOSpy.postSignUp(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getUser());
        Assertions.assertNotNull(response.getToken());
        Assertions.assertEquals(response.getUser(), user6);
    }

    @Test
    void testPostUser_DuplicateAlias(){
        String password = "password123";
        SignUpRequest request = new SignUpRequest(testUser, password, "" );
        SignUpResponse response = userDAOSpy.postSignUp(request);

        Assertions.assertEquals(response.getMessage(), "[Bad Request]: Alias is already taken");
        Assertions.assertNull(response.getUser());
    }

    @Test
    void testPostUser_sameName_differentAlias(){
        String password = "password123";
        User user = new User("Daffy", "Duck", "@ducky", "");
        SignUpRequest request = new SignUpRequest(user, password, "" );
        SignUpResponse response = userDAOSpy.postSignUp(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getUser());
        Assertions.assertNotNull(response.getToken());
        Assertions.assertEquals(response.getUser(), user);
    }

    /*--------------------------------SIGN OUT--------------------------------------------*/

    @Test
    void testSignOut(){
        userDAOSpy.setCurrentUser(testUser);
        Assertions.assertEquals(testUser, userDAOSpy.getCurrentUser());

        userDAOSpy.signOut();

        Assertions.assertNull(userDAOSpy.getCurrentUser());
    }

    /*--------------------------------SEARCH USER--------------------------------------------*/

    @Test
    void testGetUser_validUser(){
        SearchRequest request = new SearchRequest(testUser.getAlias(), null);
        SearchResponse response = userDAOSpy.getUser(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(testUser, response.getUser());
        Assertions.assertFalse(response.isFollowing());
    }

    @Test
    void testGetUser_nonExistentUser(){
        SearchRequest request = new SearchRequest("@randomAlias", null);
        SearchResponse response = userDAOSpy.getUser(request);

        Assertions.assertNotNull(response);
        Assertions.assertNull(response.getUser());
        Assertions.assertEquals("Could not find user with given alias @randomAlias", response.getMessage());
    }



    /*--------------------------------Helper functions ------------------------------------*/

    private Map<String, String> buildTestPasswords(){
        Map<String, String> map = new HashMap<>();
        map.put("@TestUser", password1);
        map.put("@DaffyDuck", password2);
        return map;
    }

    private Map<String, String> buildTestAuths(){
        Map<String, String> map = new HashMap<>();
        map.put("@TestUser", "123");
        return map;
    }
}