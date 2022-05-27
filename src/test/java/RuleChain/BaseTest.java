package RuleChain;

import io.restassured.path.json.JsonPath;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BaseTest {
    static final String BASE_URL = "http://localhost:8081";
    static final String RULE_CHAIN = "/api/ruleChain";
    static final String ENDPOINT = "/{id}";
    private static final String LOGIN = "/login";

    private final String LOGIN_TENANT = "tenant@thingsboard.org";
    private final String PASSWORD_TENANT = "tenant";


}
