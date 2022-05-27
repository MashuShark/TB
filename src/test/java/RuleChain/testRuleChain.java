package RuleChain;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class testRuleChain extends BaseTest {

    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZW5hbnRAdGhpbmdzYm9hcmQub3JnIiwic2NvcGVzIjpbIlRFTkFOVF9BRE1JTiJdLCJ1c2VySWQiOiJkOThjNWMwMC00MzRkLTExZWMtYmVlMy1iZGEwZjk1NmNmMTkiLCJlbmFibGVkIjp0cnVlLCJpc1B1YmxpYyI6ZmFsc2UsInRlbmFudElkIjoiZDhhNTdlNzAtNDM0ZC0xMWVjLWJlZTMtYmRhMGY5NTZjZjE5IiwiY3VzdG9tZXJJZCI6IjEzODE0MDAwLTFkZDItMTFiMi04MDgwLTgwODA4MDgwODA4MCIsImlzcyI6InRoaW5nc2JvYXJkLmlvIiwiaWF0IjoxNjUzNjQ2NzE5LCJleHAiOjE2NTM2NTU3MTl9.GeBTFnayVkivvdTz0fmv_F71El8Bwc7sBkOLrW6Zl90R1_Mlkp0q3dnLAI1j-cj__ccHQ1-mrcdHjuMqoJtaqw";
    String tenant_id = "d8a57e70-434d-11ec-bee3-bda0f956cf19";
    String body = "{\n" +
            "  \"additionalInfo\": {},\n" +
            "\n" +
            "  \"tenantId\": {\n" +
            "    \"id\": \"d8a57e70-434d-11ec-bee3-bda0f956cf19\",\n" +
            "    \"entityType\": \"TENANT\"\n" +
            "  },\n" +
            "  \"name\": \"Humidity data processing\",\n" +
            "  \"type\": \"CORE\",\n" +
            "  \"firstRuleNodeId\": null,\n" +
            "  \"root\": false,\n" +
            "  \"debugMode\": false,\n" +
            "  \"configuration\": {}\n" +
            "}";
    Map<String, String> requestHeaders = new HashMap<>() {{
        put("Content-Type", "application/json");
        put("Authorization", "Bearer " + token);
    }};

    @Test
    // create new Rule Chain by POST request
    public void createNewRuleChain(ITestContext context) {

        Response response = RestAssured.given().
                headers(requestHeaders).
                body(body).
                log().all().
                when().
                post(BASE_URL + RULE_CHAIN).
                then().
                extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "StatusCode does not match 200");

        String responseBody = response.body().asString();

        System.out.println("Response Body is =>  " + responseBody);

        String id = response.jsonPath().getString("id");
        int index = id.lastIndexOf(':');
        String lastString = id.substring(index + 1).replace("]", "");

        System.out.println("ID Rule Chain is =>  " + lastString);

        context.setAttribute("ruleChainID", lastString);
    }

    @Test (dependsOnMethods = "createNewRuleChain")
    // get Rule Chain
    public void getRuleChain(ITestContext context) {

        Response response = RestAssured.given()
                .headers(requestHeaders)
                .pathParam("id", context.getAttribute("ruleChainID").toString())
                .log().all()
                .when()
                .get(BASE_URL + RULE_CHAIN + ENDPOINT)
                .then()
                .extract().response();

        System.out.println("context result is => " + context.getAttribute("ruleChainID").toString());

        Assert.assertEquals(response.getStatusCode(), 200, "StatusCode does not match 200");

        String body = response.body().asString();
        System.out.println("Response Body is =>  " + body);
    }
}