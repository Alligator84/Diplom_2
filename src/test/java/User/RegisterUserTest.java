package User;

import Request.UserRequest;
import com.github.javafaker.Faker;
import domain.User;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterUserTest extends BaseTest {

    private User user;

    @Before
    public void beforeTest() {
        user = new User();
        Faker faker = new Faker();
        user.setName(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password(6, 7));
    }

    @Test
    public void successfulRegistration() {
        Response response = UserRequest.registerNewUser(user);
        assertEquals(200, response.then().extract().statusCode());
    }

    @Test
    public void errorWhenRegisteringAnExistingUser() {
        UserRequest.registerNewUser(user);
        Response response = UserRequest.registerNewUser(user);
        String message = response.then().extract().path("message");
        assertEquals(403, response.then().extract().statusCode());
        assertEquals("User already exists", message);
    }

    @Test
    public void errorRegistrationWithoutUserName() {
        UserRequest.registerNewUser(user);
        user.setName(null);
        Response response = UserRequest.registerNewUser(user);
        String message = response.then().extract().path("message");
        assertEquals(403, response.then().extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }

    @Test
    public void errorRegistrationWithoutEmail(){
        UserRequest.registerNewUser(user);
        user.setEmail(null);
        Response response = UserRequest.registerNewUser(user);
        String message = response.then().extract().path("message");
        assertEquals(403, response.then().extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }

    @Test
    public void errorRegistrationWithoutPassword(){
        UserRequest.registerNewUser(user);
        user.setPassword(null);
        Response response = UserRequest.registerNewUser(user);
        String message = response.then().extract().path("message");
        assertEquals(403, response.then().extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }

    @After
    public void cleanUser(){
        String token = UserRequest.registerNewUser(user).then().extract().path("accessToken");
        if (token != null){
            UserRequest.deleteUser(token);
        }
    }
}