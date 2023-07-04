package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import utilities.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Dummy_StepDefinitions {
    String endpoint;
    JSONObject requestBody;
    Response response;
    JsonPath responseJsonPath;

    @Given("User {string} base URL'ini kullanır")
    public void setBaseUrl(String baseUrl) {
        endpoint = ConfigReader.getProperty(baseUrl);
        System.out.println(endpoint);
    }
    @When("Path parametreleri için {string} kullanılır")
    public void setParams(String params) {
        endpoint = endpoint + "/" + params;
        System.out.println(endpoint);
    }
    @When("Request body {string},{string},{string} bilgileri ile olusturulur")
    public void createRequestBody(String name, String salary, String age) {
        requestBody = new JSONObject();
        requestBody.put("name", name);
        requestBody.put("salary", salary);
        requestBody.put("age", age);
    }
    @When("POST request gonderilir ve testleri yapmak icin response degerini kaydeder")
    public void doRequestAndSaveResponse() {
        response = given().contentType(ContentType.JSON).when().body(requestBody.toString()).post(endpoint);
        response.prettyPrint();
    }
    @Then("response'da status degerinin {string} olduğu kontrol edilir")
    public void assertResponseStatusCode(String statusCode) {
        responseJsonPath = response.jsonPath();
        assertEquals(statusCode, responseJsonPath.getString("status"));
    }
    @Then("response attributelerini degerlerinin {string},{string},{string} olduğunu kontrol et")
    public void assertResponseBody(String expectedName, String expectedSalary, String expectedAge) {
        responseJsonPath = response.jsonPath();
        if(expectedName.isBlank() && expectedAge.isBlank() && expectedSalary.isBlank()) {
            expectedName = null;
            expectedSalary = null;
            expectedAge = null;

            assertEquals(expectedName, responseJsonPath.getString("data.name"));
            assertEquals(expectedSalary, responseJsonPath.getString("data.salary"));
            assertEquals(expectedAge, responseJsonPath.getString("data.age"));
        }
        else {
            assertEquals(expectedName, responseJsonPath.getString("data.name"));
            assertEquals(expectedSalary, responseJsonPath.getString("data.salary"));
            assertEquals(expectedAge, responseJsonPath.getString("data.age"));
        }
    }
    @When("PUT request gonderilir ve testleri yapmak icin response degeri kaydedilir")
    public void doUpdateRequestAndSaveResponse() {
        response = given().when().body(requestBody.toString()).put(endpoint);
        response.prettyPrint();
    }

}
