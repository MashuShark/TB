package RuleChain;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.By.xpath;


public class testRuleChain extends BaseTest {

//    String token = context.get
    String tenant_id = "d8a57e70-434d-11ec-bee3-bda0f956cf19";
    String body = "{\n" +
            "  \"additionalInfo\": {},\n" +
            "\n" +
            "  \"tenantId\": {\n" +
            "    \"id\": \"d8a57e70-434d-11ec-bee3-bda0f956cf19\",\n" +
            "    \"entityType\": \"TENANT\"\n" +
            "  },\n" +
            "  \"name\": \"Temperature validation\",\n" +
            "  \"type\": \"CORE\",\n" +
            "  \"firstRuleNodeId\": null,\n" +
            "  \"root\": false,\n" +
            "  \"debugMode\": false,\n" +
            "  \"configuration\": {}\n" +
            "}";
    private WebDriver driver;
    Map<String, String> requestHeaders = new HashMap<>() {{
        put("Content-Type", "application/json");
    }};

    @Test
    // create new Rule Chain by POST request
    public void createNewRuleChain(ITestContext context) {

        Response response = RestAssured.given().
                headers(requestHeaders).
                header("Authorization", "Bearer " + context.getAttribute("token")).
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

//    @Test
//    public void upgradeRuleChain(){
//
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get(BASE_URL);
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
//        wait.until(
//                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
//        driver.findElement(xpath("//span[text()='Rule chains']")).click();
//        wait.until(
//                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
//        driver.findElement(xpath("//mat-row/mat-cell[contains(@class,'cdk-column-name')][1]")).click();
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(xpath("//mat-drawer[contains(@class,'mat-drawer-opened')]"))));
//        driver.findElement(xpath("//button/span[text()=' Open rule chain ']")).click();
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(xpath("//div/span[text()='Input']"))));
//
//
//        driver.quit();


//    }
}