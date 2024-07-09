package be.cbtw.apiTest;




import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class zippopotamTest {

    @Test
    public void validateListOfBookTest() {
        given()
                .when()

                .get("https://bookstore.toolsqa.com/BookStore/v1/Books")
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void validateNumberOfPageForGivenBookTest() {
        given()
                .when()
                .queryParam("ISBN", "9781449325862")
                .get("https://bookstore.toolsqa.com/BookStore/v1/Book")
                .then()
                .assertThat()
                .statusCode(200)
                .body("pages",equalTo(234));

    }

    @Test
    public void validateRandomUserUsingGPath() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .then()
                .assertThat()
                .body("userId",equalTo(1))
                .body("user.address.geo.lat",equalTo("-37.3159"))
                .body("user.address.geo.log",equalTo("-81.1496"));


    }





        @Test
        public void serviceWithAuthenticationTest() {
          given()
                 .auth()
                  .basic("postman", "password")
                  .when()
                  .get("https://postman-echo.com/basic-auth")
                  .then()
                  .assertThat()
                  .body("authenticated", equalTo(true))
                  .statusCode(200);
        }

    @Test
    public void serviceWithAuthenticationUsingPreemptiveTest() {
        given()
                .auth()
                .preemptive()
                .basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .assertThat()
                .body("authenticated", equalTo(true))
                .statusCode(200);
    }


        @Test
        public void testOAuth2Authentication() {
            // Replace with your actual GitHub OAuth token
            String accessToken = "your_github_oauth_token";

            RestAssured.baseURI = "https://api.github.com";

            given()
                    .auth()
                    .oauth2(accessToken)
                    .when()
                    .get("/user")
                    .then()
                    .statusCode(200)
                    .body("login", equalTo("your_github_username")); // Replace with your GitHub username
        }








}
