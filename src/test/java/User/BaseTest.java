package User;

import io.restassured.RestAssured;
import org.junit.Before;

public class BaseTest {

    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URI;
    }
}