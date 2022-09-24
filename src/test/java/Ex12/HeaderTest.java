package Ex12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class HeaderTest {

    @Test
    public void testHeader() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        assertEquals("Some secret value", response.getHeader("x-secret-homework-header"),
                     "There is no such header");
    }
}
