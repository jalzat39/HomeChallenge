package ip.swagger.petstore;

import dto.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;

/**
 * Test class for performing CRUD operations on User objects.
 */
public class UserTest {

    private int randomNum;
    private User user;
    private static final String BASE_URI = "http://localhost:8080/api/v3";

    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        randomNum = ThreadLocalRandom.current().nextInt(1, 101);
    }

    @Test(priority = 1)
    public void shouldCreateUserWithValidDetailsAndVerifyStatusCode200() {
        // Arrange: Set up the User object with valid details
        user = new User(randomNum, "TestUser", "TestFirstName", "TestLastName", "test@test.com", "TestPassword", "1234567890", 1);

        // Act: Send a POST request to create the user
        JsonPath jsonPath = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/user")
                .then()
                .statusCode(200)
                .log().body()
                .extract().body().jsonPath();

        // Act: Send a GET request to retrieve the created user and verify details
        RestAssured.given()
                .when()
                .get("/user/" + user.getUsername())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log().body()
                .body("id", equalTo(user.getId()))
                .body("username", equalToCompressingWhiteSpace(user.getUsername()))
                .body("firstName", equalToCompressingWhiteSpace(user.getFirstName()))
                .body("lastName", equalToCompressingWhiteSpace(user.getLastName()))
                .body("email", equalToCompressingWhiteSpace(user.getEmail()))
                .body("password", equalToCompressingWhiteSpace(user.getPassword()))
                .body("phone", equalToCompressingWhiteSpace(user.getPhone()))
                .body("userStatus", equalTo(user.getUserStatus()));
    }

    @Test(priority = 2)
    public void shouldLoginWithValidUserCredentials() {
        // Act: Send a GET request to login with valid credentials
        RestAssured.given()
                .queryParam("username", user.getUsername())
                .queryParam("password", user.getPassword())
                .when()
                .get("/user/login")
                .then()
                .statusCode(200);
    }

    @Test(priority = 3)
    public void shouldReturn400ForInvalidLoginAttempt() {
        // Act: Send a GET request to login with invalid credentials
        RestAssured.given()
                .queryParam("username", user.getUsername())
                .queryParam("password", "invalidPassword")
                .when()
                .get("/user/login")
                .then()
                .statusCode(400);
    }

    @Test(priority = 4)
    public void shouldDeleteUser() {
        // Act: Send a DELETE request to delete the user
        RestAssured.given()
                .when()
                .delete("/user/" + user.getId())
                .then()
                .statusCode(200);
    }
}
