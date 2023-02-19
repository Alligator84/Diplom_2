package Request;

import domain.Human;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserRequest {

    public static Response registerNewUser (Human user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register");
    }
    public static void deleteUser (String token){
        given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .when()
                .delete("/api/auth/user");
    }
}