package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utilities.ConfigReader;

import java.util.List;
import java.util.Map;

public class BookStore_StepDefinitions {

    String USER_ID;
    String USERNAME;
    private static final String PASSWORD = "Test@123.";
    String BASE_URL;

    private static String token;
    private static Response response;
    private static String jsonString;
    private static String bookId;

    @Given("I am {string} an authorized user in the {string} URL")
    public void iAmAnAuthorizedUser(String user_name, String base_url) {
        BASE_URL = ConfigReader.getProperty(base_url);
        USERNAME = ConfigReader.getProperty(user_name);

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");
        response = request.body("{ \"userName\":\"" + USERNAME + "\", \"password\":\"" + PASSWORD + "\"}")
                .post("/Account/v1/GenerateToken");

        String jsonString = response.asString();
        token = JsonPath.from(jsonString).get("token");

    }

    @Given("A list of books are available in the {string}")
    public void listOfBooksAreAvailable(String base_url) {
        BASE_URL = ConfigReader.getProperty(base_url);

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get("/BookStore/v1/Books");

        jsonString = response.asString();
        List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
        Assert.assertTrue(books.size() > 0);

        bookId = books.get(0).get("isbn");
    }
    @When("I {string} add a book to my reading list int the {string}")
    public void addBookInList(String user_id, String base_url) {
        USER_ID = ConfigReader.getProperty(user_id);
        BASE_URL = ConfigReader.getProperty(base_url);

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        response = request.body("{ \"userId\": \"" + USER_ID + "\", " +
                        "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookId + "\" }]}")
                .post("/BookStore/v1/Books");
    }
    @Then("The book is added")
    public void bookIsAdded() {
        Assert.assertEquals(201, response.getStatusCode());
    }
    @When("I remove a book from my reading list in the {string}")
    public void removeBookFromList(String base_url) {
        BASE_URL = ConfigReader.getProperty(base_url);

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        response = request.body("{ \"isbn\": \"" + bookId + "\", \"userId\": \"" + USER_ID + "\"}")
                .delete("/BookStore/v1/Book");
    }
    @Then("The book is removed in the {string}")
    public void bookIsRemoved(String base_url) {
        BASE_URL = ConfigReader.getProperty(base_url);

        Assert.assertEquals(204, response.getStatusCode());

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        response = request.get("/Account/v1/User/" + USER_ID);
        Assert.assertEquals(200, response.getStatusCode());

        jsonString = response.asString();
        List<Map<String, String>> booksOfUser = JsonPath.from(jsonString).get("books");
        Assert.assertEquals(0, booksOfUser.size());
    }

}
