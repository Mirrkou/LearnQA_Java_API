package Ex7;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirectTest {

    @Test
    public void testRedirect() {
        Response response1 = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String firstHeader = response1.getHeader("Location");
        System.out.println(firstHeader);
        System.out.println(response1.getStatusCode());

        if (response1.getStatusCode() == 301) {
            Response response2 = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(firstHeader)
                    .andReturn();

            String secondHeader = response2.getHeader("Location");
            System.out.println(secondHeader);
            System.out.println(response2.getStatusCode());

            if (response2.getStatusCode() == 301) {
                Response response3 = RestAssured
                        .given()
                        .redirects()
                        .follow(false)
                        .when()
                        .get(secondHeader)
                        .andReturn();

                String thirdHeader3 = response3.getHeader("Location");
                System.out.println(thirdHeader3);
                System.out.println(response3.getStatusCode());

                if (response3.getStatusCode() == 301) {
                    Response response4 = RestAssured
                            .given()
                            .redirects()
                            .follow(false)
                            .when()
                            .get(thirdHeader3)
                            .andReturn();

                    String fourthHeader = response4.getHeader("Location");
                    System.out.println(fourthHeader);
                    System.out.println(response4.getStatusCode());
                }
            }
        }
    }
}
