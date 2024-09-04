package ip.swagger.petstore;

import dto.Category;
import dto.Pet;
import dto.Status;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

/**
 * Test class for performing CRUD operations on Pet objects.
 */
public class PetStoreTest {

    private static final String PHOTO_URL = "https://images.dog.ceo/breeds/labrador/n02099712_5008.jpg";
    private int randomNum;
    private Pet pet;

    @BeforeTest
    public void setUp() {
        baseURI = "http://localhost:8080/api/v3";
        randomNum = ThreadLocalRandom.current().nextInt(1, 101); // Generate a random number between 1 and 100
    }

    /**
     * Test to verify that creating a pet with valid details returns a
     * 200 OK status code and that the created pet's details are correct.
     */
    @Test(priority = 1)
    public void shouldCreatePetWithValidDetailsAndVerifyStatusCode200() {
        // Arrange: Set up the Category and Pet objects with valid details
        Category category = new Category(34, "Sporting Group");

        Category tag = new Category(34, "Sporting");

        pet = new Pet(randomNum, category, "PetTest", Collections.singletonList(PHOTO_URL), Collections.singletonList(tag), Status.AVAILABLE);

        // Act: Send a POST request to create the pet
        JsonPath jsonPath = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .log().body()
                .extract().body().jsonPath();

        // Act: Send a GET request to retrieve the created pet and verify the details
        RestAssured.given()
                .when()
                .get("/pet/" + pet.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log().body()
                .body("id", equalTo(pet.getId()))
                .body("name", equalToCompressingWhiteSpace(pet.getName()))
                .body("status", equalToCompressingWhiteSpace(pet.getStatus().toString()))
                .body("category.id", equalTo(pet.getCategory().getId()))
                .body("category.name", equalTo(pet.getCategory().getName()))
                .body("tags[0].id", equalTo(pet.getTags().get(0).getId()))
                .body("tags[0].name", equalTo(pet.getTags().get(0).getName()))
                .body("photoUrls[0]", containsStringIgnoringCase("labrador"));
    }

    /**
     * Test to verify that making a GET request to the /pet endpoint returns a
     * 405 Method Not Allowed status code, which indicates that GET requests are
     * not supported at this endpoint.
     */
    @Test(priority = 8)
    public void shouldReturn405ForGetRequestToPetEndpoint() {
        RestAssured.given()
                .when()
                .get("/pet")
                .then()
                .statusCode(405);
    }

    /**
     * Test to verify that updating a pet's details using a POST request with
     * the pet ID returns a 200 OK status code.
     */
    @Test(priority = 2)
    public void shouldUpdatePetDetailsAndReturn200() {
        // Arrange: Define the parameters for the update
        String updatedName = "Test2";
        String updatedStatus = "pending";

        // Act: Send a POST request to update the pet's details
        RestAssured.given()
                .queryParam("name", updatedName)
                .queryParam("status", updatedStatus)
                .when()
                .post("/pet/" + pet.getId())
                .then()
                .statusCode(200);
    }

    /**
     * Test to verify that a POST request to update a pet's details with an
     * invalid or missing pet ID returns a 400 Bad Request status code.
     */
    @Test(priority = 3)
    public void shouldReturn400ForUpdateWithInvalidPetID() {
        // Arrange: Define the parameters for the update request
        String updatedName = "Test2";
        String updatedStatus = "pending";

        // Act: Send a POST request with an invalid or missing pet ID
        RestAssured.given()
                .queryParam("name", updatedName)
                .queryParam("status", updatedStatus)
                .when()
                .post("/pet/")
                .then()
                .statusCode(400);
    }

    /**
     * Test to verify that retrieving pet data with a valid pet ID returns a
     * 200 OK status code and that the pet details match the expected values.
     */
    @Test(priority = 4)
    public void shouldReturnPetDataAndStatus200ForValidPetId() {
        // Arrange: Define the expected pet details
        String expectedName = "Test2";
        String expectedStatus = "pending";

        // Act: Send a GET request to retrieve the pet's data and extract the response
        JsonPath jsonPath = RestAssured.given()
                .when()
                .get("/pet/" + pet.getId())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();

        // Assert: Verify that the pet details match the expected values
        assertEquals(expectedName, jsonPath.get("name"), "Pet name does not match");
        assertEquals(expectedStatus, jsonPath.get("status"), "Pet status does not match");
    }

    /**
     * Test to verify that deleting a pet with a valid pet ID returns a
     * 200 OK status code.
     */
    @Test(priority = 5)
    public void shouldReturn200ForDeletePetWithValidPetId() {
        // Arrange: Set up any necessary data or headers
        String apiKey = "token";

        // Act: Send a DELETE request to remove the pet
        RestAssured.given()
                .header("api_key", apiKey)
                .when()
                .delete("/pet/" + pet.getId())
                .then()
                .statusCode(200);
    }

    /**
     * Test to verify that attempting to delete a pet with an invalid or missing
     * pet ID returns a 405 Method Not Allowed status code.
     */
    @Test(priority = 6)
    public void shouldReturn405ForDeleteRequestWithInvalidPetId() {
        // Arrange: Set up any necessary data or headers
        String apiKey = "token";

        // Act: Send a DELETE request with an invalid or missing pet ID
        RestAssured.given()
                .header("api_key", apiKey)
                .when()
                .delete("/pet/")
                .then()
                .statusCode(405);
    }

    /**
     * Test to verify that a GET request to retrieve a pet with an invalid pet ID
     * returns a 404 Not Found status code.
     */
    @Test(priority = 7)
    public void shouldReturn404ForGetRequestWithInvalidPetId() {
        // Arrange: Define an invalid pet ID
        String invalidPetId = "100";

        // Act: Send a GET request to retrieve the pet's data with the invalid ID
        Response response = RestAssured.given()
                .when()
                .get("/pet/" + invalidPetId);

        // Assert: Verify that the response status code is 404 Not Found
        assertEquals(response.getStatusCode(), 404, "Expected status code to be 404 for invalid pet ID.");
    }
}