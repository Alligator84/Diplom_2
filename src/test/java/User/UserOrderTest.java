package User;

import Request.OrderRequest;
import Request.UserRequest;
import domain.Autorization;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserOrderTest extends BaseTest {

    private String token;

    @Before
    public void beforeTest() {
        Autorization autorization = new Autorization();
        token = UserRequest.registerNewUser(autorization).then().extract().path("accessToken");
    }

    @Test
    public void successGetUserOrder(){
        Response response = OrderRequest.getUserOrder(token);
        Assert.assertEquals(200, response.then().extract().statusCode());
        Assert.assertTrue(response.then().extract().path("success"));
    }
    @Test
    public void getUserOrderNoAuthorization(){
        Response response = OrderRequest.getUserOrder("");
        String message = response.then().extract().path("message");
        Assert.assertEquals(401, response.then().extract().statusCode());
        Assert.assertEquals("You should be authorised", message);
    }

    @After
    public void cleanUp(){
        UserRequest.deleteUser(token);
    }
}