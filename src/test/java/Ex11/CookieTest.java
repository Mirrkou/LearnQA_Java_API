package Ex11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CookieTest {

    @Test
    public void testCookie() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Map<String, String> cookies = response.getCookies();

        assertEquals("hw_value", response.getCookie("HomeWork"),"Wrong cookie");
        assertTrue(cookies.containsValue("hw_value"));
        assertTrue(cookies.containsKey("HomeWork"));
    }
}
