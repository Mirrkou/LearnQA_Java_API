package Ex8;

import static java.lang.Thread.sleep;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class TokenTest {

    @Test
    public void testToken() throws InterruptedException {

        String statusNotReady = "Job is NOT ready";
        String statusReady = "Job is ready";

        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        response.prettyPrint();

        if (response.getInt("seconds") > 8) {

            String token = response.getString("token");

            Map<String, String> params = new HashMap<>();
            params.put("token", token);

            JsonPath response2 = RestAssured
                    .given()
                    .queryParams(params)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();

            if (response2.getString("status").equals(statusNotReady)) {
                System.out.println("Задача не готова" + "\nСтатус: " + response2.getString("status"));

                sleep(20000);

                JsonPath response3 = RestAssured
                        .given()
                        .queryParams(params)
                        .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                        .jsonPath();

                if (response3.getString("status").equals(statusReady)) {
                    System.out.println("Задача готова \nРезультат: " + response3.getString("result") + "\nСтатус: "
                                       + response3.getString("status"));
                }
            }
        } else {
            System.out.println("Задача не успевает создаться, не проверить статус Job is NOT ready");
        }
    }
}

