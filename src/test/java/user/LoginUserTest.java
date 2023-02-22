package user;

import request.LoginRequest;
import request.UserRequest;
import domain.Autorization;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginUserTest extends BaseTest {

    private Autorization autorization;
    private String token;

    @Before
    public void beforeTest() {
        autorization = new Autorization();
        token = UserRequest.registerNewUser(autorization).then().extract().path("accessToken");
    }

    @Test
    public void successfulLogin() {
        Response response = LoginRequest.login(autorization);
        assertEquals(200, response.then().extract().statusCode());
        Boolean success = response.then().extract().path("success");
        assertEquals(true, success);
    }

    @Test
    public void errorLoginWithWrongEmail(){
        Autorization autorization = new Autorization();
        autorization.setEmail("121212@121212.ru");
        Response response = LoginRequest.login(autorization);
        assertEquals(401, response.then().extract().statusCode());
        String message = response.then().extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
    @Test
    public void errorLoginWithWrongPassword(){
        Autorization autorization = new Autorization();
        autorization.setPassword("88888888");
        Response response = LoginRequest.login(autorization);
        assertEquals(401, response.then().extract().statusCode());
        String message = response.then().extract().path("message");
        assertEquals("email or password are incorrect", message);
    }

    @After
    public void cleanUp(){
        UserRequest.deleteUser(token);
    }
}