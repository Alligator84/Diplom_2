package Request;

import domain.Ingredients;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderRequest {

    public static Response createOrder(Ingredients ingredients, String token){
        return given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .body(ingredients)
                .when()
                .post("/api/orders");
    }
    public static Response getUserOrder(String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .get("/api/orders");
    }
}