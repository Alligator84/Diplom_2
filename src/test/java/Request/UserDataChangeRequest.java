package Request;

import domain.ChangeUserData;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserDataChangeRequest {

    public static Response changeUserData (ChangeUserData changeUserData, String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(changeUserData)
                .when()
                .patch("/api/auth/user");
    }
}