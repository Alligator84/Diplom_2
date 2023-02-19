package Request;

import domain.Autorization;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginRequest {

    public static Response login (Autorization autorization){
        return given()
                .header("Content-type", "application/json")
                .body(autorization)
                .when()
                .post("/api/auth/login");
    }
}