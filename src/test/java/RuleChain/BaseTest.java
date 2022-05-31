package RuleChain;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    static final String BASE_URL = "http://localhost:8081";
    static final String RULE_CHAIN = "/api/ruleChain";
    static final String ENDPOINT = "/{id}";
    private static final String LOGIN = "/api/auth/login";

    String body = "{ \"username\": \"tenant@thingsboard.org\", \"password\": \"tenant\"}";

    @BeforeSuite
    public void requestToken(ITestContext context) {

        Response response = RestAssured.given().
                contentType(ContentType.JSON).
                body(body).
                log().all().
                when().
                post(BASE_URL + LOGIN).
                then().
                extract().response();
        System.out.println(response.asString());
        context.setAttribute("token", response.jsonPath().getString("token"));
    }
}
