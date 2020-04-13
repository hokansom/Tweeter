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
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.SignUpResponse;
import edu.byu.cs.tweeter.server.dao.signUp.SignUpDAOMock;


class SignUpDAOTest {

    private final User testUser = new User("Test", "User", UserGenerator.MALE_IMAGE_URL);
    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", "");
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", "");

    private final List<User> users = Arrays.asList(testUser, user1, user2, user3, user4);

    private final String password1 = "dc647eb65e6711e155375218212b3964";
    private final String password2 = "7f2ababa423061c509f4923dd04b6cf1";

    private Map<String, String> passwordsMap;

    private SignUpDAOMock signUpDAOSpy;

    @BeforeEach
    void setup(){
        signUpDAOSpy = Mockito.spy(new SignUpDAOMock());

        UserGenerator mockUserGenerator = Mockito.mock(UserGenerator.class);
        Mockito.when(mockUserGenerator.generateUsers(Mockito.anyInt())).thenReturn(users);

        passwordsMap = buildTestPasswords();
        Mockito.when(mockUserGenerator.generatePasswords(Mockito.anyListOf(User.class))).thenReturn(passwordsMap);

        Mockito.when(signUpDAOSpy.getUserGenerator()).thenReturn(mockUserGenerator);
    }

    @Test
    void testSignUp_validUser(){
        String password = "password123";
        SignUpRequest request = new SignUpRequest(user5, password, "");
        SignUpResponse response = signUpDAOSpy.postSignUp(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getUser());
        Assertions.assertNotNull(response.getToken());
        Assertions.assertEquals(response.getUser(), user5);
    }

    @Test
    void testPostUser_DuplicateAlias(){
        String password = "password123";
        SignUpRequest request = new SignUpRequest(testUser, password, "" );
        SignUpResponse response = signUpDAOSpy.postSignUp(request);

        Assertions.assertEquals(response.getMessage(), "[Bad Request]: Alias is already taken");
        Assertions.assertNull(response.getUser());
    }

    @Test
    void testPostUser_sameName_differentAlias(){
        String password = "password123";
        User user = new User("Daffy", "Duck", "@ducky", "");
        SignUpRequest request = new SignUpRequest(user, password, "" );
        SignUpResponse response = signUpDAOSpy.postSignUp(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getUser());
        Assertions.assertNotNull(response.getToken());
        Assertions.assertEquals(response.getUser(), user);
    }


    private Map<String, String> buildTestPasswords(){
        Map<String, String> map = new HashMap<>();
        map.put("@TestUser", password1);
        map.put("@DaffyDuck", password2);
        return map;
    }

}