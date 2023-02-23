package order;

import request.OrderRequest;
import request.UserRequest;
import user.BaseTest;
import domain.Autorization;
import domain.Ingredients;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateOrderTest extends BaseTest {

    private final String id = "61c0c5a71d1f82001bdaaa6d";
    private String token;

    @Before
    public void beforeTest() {
        token = UserRequest.registerNewUser(new Autorization()).then().extract().path("accessToken");
    }

    @Test
    public void successfulCreateOrder() {
        Ingredients ingredients = new Ingredients(id);
        Response response = OrderRequest.createOrder(ingredients, token);
        Boolean success = response.then().extract().path("success");
        Assert.assertEquals(200, response.then().extract().statusCode());
        Assert.assertTrue(success);

    }

    @Test
    public void successfulCreateOrderNoAuthorization() {
        Ingredients ingredients = new Ingredients(id);
        Response response = OrderRequest.createOrder(ingredients, "");
        Boolean success = response.then().extract().path("success");
        Assert.assertEquals(200, response.then().extract().statusCode());
        Assert.assertTrue(success);
    }

    @Test
    public void errorCreateOrderWithoutIngredients() {
        Ingredients ingredients = new Ingredients("");
        Response response = OrderRequest.createOrder(ingredients, token);
        String message = response.then().extract().path("message");
        Assert.assertEquals(400, response.then().extract().statusCode());
        Assert.assertEquals("Ingredient ids must be provided", message);
    }

    @Test
    public void errorCreateOrderWrongHashOfIngredients() {
        Ingredients ingredients = new Ingredients("wrong id");
        Response response = OrderRequest.createOrder(ingredients, token);
        Assert.assertEquals(500, response.then().extract().statusCode());
    }

    @After
    public void cleanUp() {
        UserRequest.deleteUser(token);
    }
}