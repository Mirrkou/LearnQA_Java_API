package Ex14.tests;

import Ex14.lib.ApiCoreRequests;
import Ex14.lib.Assertions;
import Ex14.lib.BaseTestCase;
import Ex14.lib.DataGenerator;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAUth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAUth, 400);
        Assertions.assertResponseTextEquals(responseCreateAUth,
                                            "Users with email '" + email + "' already exists");
    }

    @Test
    public void testCreateUserSuccessfully() {
        String email = DataGenerator.getRandomEmail();

        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAUth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAUth, 200);
        Assertions.assertJsonHasField(responseCreateAUth, "id");
    }

    @Test
    public void testCreateUserWithIncorrectEmail() {
        String email = "vinkotovexample.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAUth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAUth, 400);
        Assertions.assertResponseTextEquals(responseCreateAUth, "Invalid email format");
    }

    @Test
    public void testCreateUserWithShortName() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("username", "l");
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAUth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAUth, 400);
        Assertions.assertResponseTextEquals(responseCreateAUth, "The value of 'username' field is too short");
    }

    @Test
    public void testCreateUserWithLongName() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("username", DataGenerator.getRandomUsername());
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAUth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAUth, 400);
        Assertions.assertResponseTextEquals(responseCreateAUth, "The value of 'username' field is too long");
    }

    @ParameterizedTest
    @CsvSource(value = {"password, username, firstName, lastName"})
    public void testCreateUserWithoutAnyField(String password, String username, String firstname, String lastname) {

        Map<String, String> userData = new HashMap<>();
        userData.put("password", password);
        userData.put("username", username);
        userData.put("firstName", firstname);
        userData.put("lastName", lastname);

        Response responseCreateAUth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAUth, 400);
        Assertions.assertResponseTextEquals(responseCreateAUth, "The following required params are missed: email");
    }
}
