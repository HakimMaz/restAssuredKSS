package be.cbtw.apiTest;


import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
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
                .body("pages", equalTo(234));

    }

    @Test
    public void validateRandomUserUsingGPath() {
        given()
                .when()
                .get("http://localhost:3000/posts/1")
                .then()
                .assertThat(
                ).body("id", equalTo("1"))
                .body("title", equalTo("Mock Post 1"))
                .body("author.name", equalTo("John Doe"))
                .body("author.address.city", equalTo("Springfield"))
                .body("author.company.name", equalTo("ACME Inc."))
                .body("comments.size()", equalTo(2))
                .body("comments[0].body", equalTo("This is comment 1"))
                .body("comments[1].user.name", equalTo("Michael Johnson"));
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

// need to have key github account
//    @Test
//    public void testOAuth2Authentication() {
//        // Replace with your actual GitHub OAuth token
//        String accessToken = "your_github_oauth_token";
//
//        RestAssured.baseURI = "https://api.github.com";
//
//        given()
//                .auth()
//                .oauth2(accessToken)
//                .when()
//                .get("/user")
//                .then()
//                .statusCode(200)
//                .body("login", equalTo("your_github_username")); // Replace with your GitHub username
//    }

    @Test
    public void JsonSchemaValidationTest() {
        given()
                .when()

                .get("https://bookstore.toolsqa.com/BookStore/v1/Books")
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("be.cbtw.apiTest.schema/list-book-schema-validator.json"))
                .log();

    }


}
