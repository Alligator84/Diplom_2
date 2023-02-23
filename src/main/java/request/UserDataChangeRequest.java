package request;

import domain.ChangeUserData;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import util.Util;

import static io.restassured.RestAssured.given;

public class UserDataChangeRequest {

    @Step("Изменение данных пользователя")
    public static Response changeUserData(ChangeUserData changeUserData, String token) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(changeUserData)
                .when()
                .patch(Util.URL_USER);
    }
}