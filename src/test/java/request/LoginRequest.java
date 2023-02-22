package request;

import domain.Autorization;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import util.Util;

import static io.restassured.RestAssured.given;

public class LoginRequest {

    @Step("Логин пользователя")
    public static Response login (Autorization autorization){
        return given()
                .header("Content-type", "application/json")
                .body(autorization)
                .when()
                .post(Util.URL_LOGIN);
    }
}