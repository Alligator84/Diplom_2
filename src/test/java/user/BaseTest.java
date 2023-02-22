package user;

import io.restassured.RestAssured;
import org.junit.Before;
import util.Util;

public class BaseTest {

    public static final String BASE_URI = Util.BASE_URL;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }
}