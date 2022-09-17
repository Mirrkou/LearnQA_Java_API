package Ex5;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class JSONParsingTest {

    @Test
    public void testJSONParsing() {
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        response.prettyPrint();

        String messages = response.getString("messages.message[1]");
        System.out.println(messages);
    }
}
