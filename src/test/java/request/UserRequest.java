package request;

import domain.Human;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import util.Util;

import static io.restassured.RestAssured.given;

public class UserRequest {

    @Step("Регистрация нового пользователя")
    public static Response registerNewUser (Human user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(Util.URL_REGISTER);
    }

    @Step("Удаление пользователя")
    public static void deleteUser (String token){
        given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .when()
                .delete(Util.URL_USER);
    }
}