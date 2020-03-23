package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.SignInRequest;
import edu.byu.cs.tweeter.model.service.response.SignInResponse;

class SignInDAOTest {


    private final User testUser = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);
    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", "");
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", "");

    private final List<User> users = Arrays.asList(testUser, user1, user2, user3, user4, user5);

    private final String password1 = "dc647eb65e6711e155375218212b3964";
    private final String password2 = "7f2ababa423061c509f4923dd04b6cf1";

    private Map<String, String> passwordsMap;

    private Map<String, String> auths;

    private SignInDAO signInDAOSpy;


    @BeforeEach
    void setup(){
        signInDAOSpy = Mockito.spy(new SignInDAO());

        UserGenerator mockUserGenerator = Mockito.mock(UserGenerator.class);
        Mockito.when(mockUserGenerator.generateUsers(Mockito.anyInt())).thenReturn(users);

        passwordsMap = buildTestPasswords();

        Mockito.when(mockUserGenerator.generatePasswords(Mockito.anyListOf(User.class))).thenReturn(passwordsMap);

        auths = buildTestAuths();
        Mockito.when(mockUserGenerator.generateTokens(Mockito.anyListOf(User.class))).thenReturn(auths);

        Mockito.when(signInDAOSpy.getUserGenerator()).thenReturn(mockUserGenerator);

    }

    @Test
    void testSignIn_validUser(){
        SignInRequest request = new SignInRequest("@TestUser", "Password");
        SignInResponse response = signInDAOSpy.postSignIn(request);

        Assertions.assertNotNull(response.getUser());
        Assertions.assertEquals("Test User", response.getUser().getName());
        Assertions.assertEquals("@TestUser", response.getUser().getAlias());
        Assertions.assertNotNull(response.getToken());
    }

    @Test
    void testSignIn_validUser_WrongPassword(){
        SignInRequest request = new SignInRequest("@TestUser", "random123");
        SignInResponse response = signInDAOSpy.postSignIn(request);

        Assertions.assertNull(response.getUser());
        Assertions.assertEquals("Bad Request: Invalid alias or password", response.getMessage());
        Assertions.assertNull(response.getToken());
    }

    @Test
    void testSignIn_nonexistingUser(){
        SignInRequest request = new SignInRequest("@RandomUser", "Password");
        SignInResponse response = signInDAOSpy.postSignIn(request);

        Assertions.assertNull(response.getUser());
        Assertions.assertEquals("Bad Request: User with given alias (@RandomUser) does not exist.", response.getMessage());
        Assertions.assertNull(response.getToken());
    }


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