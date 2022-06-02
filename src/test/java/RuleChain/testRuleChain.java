package RuleChain;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class testRuleChain extends BaseTest {

    String tenant_id = "d8a57e70-434d-11ec-bee3-bda0f956cf19";

    //    String createNewRuleChainBodyS = "{\n" +
//            "  \"additionalInfo\": {},\n" +
//            "\n" +
//            "  \"tenantId\": {\n" +
//            "    \"id\": \"d8a57e70-434d-11ec-bee3-bda0f956cf19\",\n" +
//            "    \"entityType\": \"TENANT\"\n" +
//            "  },\n" +
//            "  \"name\": \"Temperature validation\",\n" +
//            "  \"type\": \"CORE\",\n" +
//            "  \"firstRuleNodeId\": null,\n" +
//            "  \"root\": false,\n" +
//            "  \"debugMode\": false,\n" +
//            "  \"configuration\": {}\n" +
//            "}";
    JSONObject additionalInfo = new JSONObject() {{
    }};
    JSONObject configurationRuleChain = new JSONObject() {{
    }};
    JSONObject createNewRuleChainBody = new JSONObject() {{

        put("additionalInfo", additionalInfo);
        JSONObject tenantIdJSON = new JSONObject() {{
            put("id", TENANT_ID);
            put("entityType", "TENANT");
        }};
        put("tenantId", tenantIdJSON);
        put("name", "test2");
        put("type", "CORE");
        put("firstRuleNodeId", JSONObject.NULL);
        put("root", false);
        put("debugMode", false);
        put("configuration", configurationRuleChain);

    }};

    JSONObject updateNewRuleChainBody = new JSONObject() {{
        JSONObject ruleChain = new JSONObject() {{
            put("additionalInfo", additionalInfo);
            put("name", "test2");
            put("type", "CORE");
            put("firstRuleNodeId", JSONObject.NULL);
            put("root", false);
            put("debugMode", false);
            put("configuration", configurationRuleChain);
        }};
        JSONObject metadata = new JSONObject() {
            {
                put("firstNodeIndex", 2);

                JSONArray nodes = new JSONArray() {{

                    JSONObject node1 = new JSONObject() {{
                        JSONObject additionalInfo = new JSONObject() {{
                            put("description", "");
                            put("layoutX", 930);
                            put("layoutY", 150);
                        }};

                        put("additionalInfo", additionalInfo);
                        put("type", "org.thingsboard.rule.engine.filter.TbJsFilterNode");
                        put("name", "Temperature validation");
                        put("debugMode", false);
                        JSONObject configuration = new JSONObject() {{
                            put("jsScript", "return typeof msg.temperature === 'undefined') || (msg.temperature >= -40 && msg.temperature <= 80);");
                        }};
                        put("configuration", configuration);
                    }};

                    JSONObject node2 = new JSONObject() {{
                        JSONObject additionalInfo = new JSONObject() {{
                            put("description", "");
                            put("layoutX", 1200);
                            put("layoutY", 220);
                        }};

                        put("additionalInfo", additionalInfo);
                        put("type", "org.thingsboard.rule.engine.action.TbLogNode");
                        put("name", "Log");
                        put("debugMode", false);
                        JSONObject configuration = new JSONObject() {{
                            put("jsScript", "return 'Incoming message:\\n' + JSON.stringify(msg) + '\\nIncoming metadata:\\n' + JSON.stringify(metadata);");
                        }};
                        put("configuration", configuration);
                    }};
                    JSONObject node3 = new JSONObject() {{
                        JSONObject additionalInfo = new JSONObject() {{
                            put("description", "");
                            put("layoutX", 320);
                            put("layoutY", 150);
                        }};

                        put("additionalInfo", additionalInfo);
                        put("type", "org.thingsboard.rule.engine.profile.TbDeviceProfileNode");
                        put("name", "Device Profile Node");
                        put("debugMode", false);
                        JSONObject configuration = new JSONObject() {{
                            put("persistAlarmRulesState", false);
                            put("fetchAlarmRulesStateOnStart", false);
                        }};
                        put("configuration", configuration);

                    }};
                    JSONObject node4 = new JSONObject() {{
                        JSONObject additionalInfo = new JSONObject() {{
                            put("description", "");
                            put("layoutX", 620);
                            put("layoutY", 150);
                        }};

                        put("additionalInfo", additionalInfo);
                        put("type", "org.thingsboard.rule.engine.filter.TbMsgTypeSwitchNode");
                        put("name", "Message Type Switch");
                        put("debugMode", false);
                        JSONObject configuration = new JSONObject() {{
                            put("version", 0);
                        }};
                        put("configuration", configuration);
                    }};
                    JSONObject node5 = new JSONObject() {{
                        JSONObject additionalInfo = new JSONObject() {{
                            put("description", "");
                            put("layoutX", 1200);
                            put("layoutY", 150);
                        }};

                        put("additionalInfo", additionalInfo);
                        put("type", "org.thingsboard.rule.engine.telemetry.TbMsgTimeseriesNode");
                        put("name", "Save Timeseries");
                        put("debugMode", false);
                        JSONObject configuration = new JSONObject() {{
                            put("defaultTTL", 0);
                        }};
                        put("configuration", configuration);
                    }};
                    put(node1);
                    put(node2);
                    put(node3);
                    put(node4);
                    put(node5);
                }};
                put("nodes", nodes);
            }
        };

        JSONArray connections = new JSONArray() {{
            JSONObject connections1 = new JSONObject() {{
                put("fromIndex", 0);
                put("toIndex", 4);
                put("type", "True");
            }};
            JSONObject connections2 = new JSONObject() {{
                put("fromIndex", 0);
                put("toIndex", 1);
                put("type", "False");
            }};
            JSONObject connections3 = new JSONObject() {{
                put("fromIndex", 2);
                put("toIndex", 3);
                put("type", "Success");
            }};
            JSONObject connections4 = new JSONObject() {{
                put("fromIndex", 3);
                put("toIndex", 0);
                put("type", "Post telemetry");
            }};
            put(connections1);
            put(connections2);
            put(connections3);
            put(connections4);
        }};

        put("ruleChain", ruleChain);
        put("metadata", metadata);
        put("connections", connections);
        put("ruleChainConnections", JSONObject.NULL);
    }};

    Map<String, String> requestHeaders = new HashMap<>() {{
        put("Content-Type", "application/json");
    }};

    @Test
    // create new Rule Chain by POST request
    public void createNewRuleChain(ITestContext context) {

        Response response = RestAssured.given().
                headers(requestHeaders).
                header("Authorization", "Bearer " + context.getAttribute("token")).
                body(createNewRuleChainBody.toString()).
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

    @Test(dependsOnMethods = "createNewRuleChain")
    // get Rule Chain
    public void getRuleChain(ITestContext context) {

        Response response = RestAssured.given()
                .headers(requestHeaders)
                .header("Authorization", "Bearer " + context.getAttribute("token"))
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

    String myRuleChainID = "cf0e4250-dcf1-11ec-b937-b3313849a972";

    @Test
    // get Rule Chain
    public void simpleGetRuleChain(ITestContext context) {

        Response response = RestAssured.given()
                .headers(requestHeaders)
                .header("Authorization", "Bearer " + context.getAttribute("token"))
                .pathParam("id", myRuleChainID)
                .log().all()
                .when()
                .get(BASE_URL + RULE_CHAIN + ENDPOINT)
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "StatusCode does not match 200");

        String body = response.body().asString();
        System.out.println("Response Body is =>  " + body);
    }

    @Test(dependsOnMethods = "createNewRuleChain")
    public void upgradeRuleChain(ITestContext context) {

        Response response = RestAssured.given().
                headers(requestHeaders).
                header("Authorization", "Bearer " + context.getAttribute("token")).
                body(updateNewRuleChainBody.toString()).
                log().all().
                when().
                post(BASE_URL + RULE_CHAIN).
                then().
                extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "StatusCode does not match 200");

        String responseBody = response.body().asString();
        System.out.println("Response Body is =>  " + responseBody);
    }
}