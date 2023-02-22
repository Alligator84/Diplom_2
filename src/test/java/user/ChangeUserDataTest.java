package user;

import request.UserDataChangeRequest;
import request.UserRequest;
import domain.Autorization;
import domain.ChangeUserData;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChangeUserDataTest extends BaseTest {

    private Autorization autorization;
    private String token;
    private ChangeUserData changeUserData;
    @Before
    public void beforeTest() {
        autorization = new Autorization();
        token = UserRequest.registerNewUser(autorization).then().extract().path("accessToken");
    }

    @Test
    public void successfulChangeEmailWithAuthorization(){
        changeUserData = new ChangeUserData(autorization.getName(), "newemail@test.com");
        Response response = UserDataChangeRequest.changeUserData(changeUserData, token);
        String email = response.then().extract().path("user.email");
        Assert.assertEquals(200, response.then().extract().statusCode());
        Assert.assertEquals("newemail@test.com", email);
    }

    @Test
    public void successfulChangeNameWithAuthorization(){
        changeUserData = new ChangeUserData("newName", autorization.getEmail());
        Response response = UserDataChangeRequest.changeUserData(changeUserData, token);
        String name = response.then().extract().path("user.name");
        Assert.assertEquals(200, response.then().extract().statusCode());
        Assert.assertEquals("newName", name);
    }

    @Test
    public void errorChangeEmailNoAuthorization(){
        changeUserData = new ChangeUserData(autorization.getName(), "newemail@test.com");
        Response response = UserDataChangeRequest.changeUserData(changeUserData, "");
        String message = response.then().extract().path("message");
        Assert.assertEquals(401, response.then().extract().statusCode());
        Assert.assertEquals("You should be authorised", message);
    }

    @Test
    public void errorChangeNameNoAuthorization(){
        changeUserData = new ChangeUserData("newName", autorization.getEmail());
        Response response = UserDataChangeRequest.changeUserData(changeUserData, "");
        String message = response.then().extract().path("message");
        Assert.assertEquals(401, response.then().extract().statusCode());
        Assert.assertEquals("You should be authorised", message);
    }

    @After
    public void cleanUp(){
        UserRequest.deleteUser(token);
    }
}