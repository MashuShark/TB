package RuleChain;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class testRuleChain {
    final String BASE_URL = "http://localhost:8081";
    final String RULE_CHAIN = "/api/ruleChain";
    final String ENDPOINT = "/{id}";
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZW5hbnRAdGhpbmdzYm9hcmQub3JnIiwic2NvcGVzIjpbIlRFTkFOVF9BRE1JTiJdLCJ1c2VySWQiOiJkOThjNWMwMC00MzRkLTExZWMtYmVlMy1iZGEwZjk1NmNmMTkiLCJlbmFibGVkIjp0cnVlLCJpc1B1YmxpYyI6ZmFsc2UsInRlbmFudElkIjoiZDhhNTdlNzAtNDM0ZC0xMWVjLWJlZTMtYmRhMGY5NTZjZjE5IiwiY3VzdG9tZXJJZCI6IjEzODE0MDAwLTFkZDItMTFiMi04MDgwLTgwODA4MDgwODA4MCIsImlzcyI6InRoaW5nc2JvYXJkLmlvIiwiaWF0IjoxNjUzNTY2NDg4LCJleHAiOjE2NTM1NzU0ODh9.NsxPh6KeLmC0cQsX5CMQ6tRLqCdaAh0UnAtMg9Jkzha-Y0kXA9zUlEfxajxZqu6HH7oYmzp_RGo2dnQS56sCZQ";
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
    public void createNewRuleChain() {

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
    }

    String ruleChainId = "cf0e4250-dcf1-11ec-b937-b3313849a972";

    //    cf0e4250-dcf1-11ec-b937-b3313849a972
    @Test
    public void getRuleChain() {
        Response response = RestAssured.given()
                .headers(requestHeaders)
                .pathParam("id", ruleChainId)
                .log().all()
                .when()
                .get(BASE_URL + RULE_CHAIN + ENDPOINT)
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "StatusCode does not match 200");
        String body = response.body().asString();
        System.out.println("Response Body is =>  " + body);
    }
}