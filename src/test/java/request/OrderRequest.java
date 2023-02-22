package request;

import domain.Ingredients;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import util.Util;

import static io.restassured.RestAssured.given;

public class OrderRequest {

    @Step("Создание заказа")
    public static Response createOrder(Ingredients ingredients, String token){
        return given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .body(ingredients)
                .when()
                .post(Util.URL_ORDERS);
    }

    @Step("Получение заказа")
    public static Response getUserOrder(String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .get(Util.URL_ORDERS);
    }
}