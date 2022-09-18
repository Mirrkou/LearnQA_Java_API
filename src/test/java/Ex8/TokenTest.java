package Ex8;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class TokenTest {

    @Test
    public void testToken() {
        Map<String, String> params = new HashMap<>();
        params.put("token", "QOxojMzojMxACOx0SOw0iMyAjM");

        String statusReady = "Job is ready";
        String statusNotReady = "Job is NOT ready";
        String error = "No job linked to this token";

        // БЕЗ GET-параметра token, метод заводит новую задачу, а в ответ выдает нам JSON с полями token, seconds
        JsonPath response1 = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        response1.prettyPrint();
        if (response1.getString("seconds").isEmpty()) {
            System.out.println("Метод не заводит новую задачу");
        } else {
            System.out.println("Метод заводит задачу, в ответ выдает JSON с полями token, seconds");
        }

        // error - будет только в случае, если передать token, для которого не создавалась задача. В этом случае в ответе будет следующая надпись - No job linked to this token
        JsonPath response2 = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job?token=111")
                .jsonPath();

        response2.prettyPrint();
        if (response2.getString("error").equals(error)) {
            System.out.println("Все плохо, нет задачки, связанной с таким токеном");
        } else {
            System.out.println("Все хорошо, но это не точно");
        }

        // делал один запрос с token ДО того, как задача готова, убеждался в правильности поля status
        // Все время Статус Job is ready?
        JsonPath response3 = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        response3.prettyPrint();
        if (response3.getString("status").equals(statusNotReady)) {
            System.out.println("Статус Job is NOT ready");
        } else {
            System.out.println("Статус Job is ready");
        }

        // result - будет только в случае, если задача готова, это поле будет содержать результат
        JsonPath response4 = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response4.prettyPrint();
        if (response4.getString("status").equals(statusReady)) {
            System.out.println("Результат: " + response4.getString("result") + "\nСтатус: "
                               + response4.getString("status"));
        } else {
            System.out.println("Нет результата и никогда не будет, вот такая грустная история");
        }
    }
}

