package be.cbtw.apiTest;


import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import io.restassured.path.xml.XmlPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class zippopotamTest {

    @Test(groups = {"group0"})
    @Story("Test verify story 1")
    public void validateListOfBookTest() {
        given()
                .when()

                .get("https://bookstore.toolsqa.com/BookStore/v1/Books")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test(groups = {"group0"})
    @Description("Adding parameters and headers to query")
    @Story("Test verify story 1")
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

    @Test(groups = {"group0"})
    @Story("Test verify story 1")
    public void validateNumberOfPageForGivenBookWithHeaderTest() {
        given()
                .when()
                .queryParam("ISBN", "9781449325862")
                .get("https://bookstore.toolsqa.com/BookStore/v1/Book")
                .then()
                .assertThat()
                .statusCode(200)
                .body("pages", equalTo(234));
    }
    @Test(groups = {"group0"})
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

    @Test(groups = {"group0"})
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

    @Test(groups = {"group0"})
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

    @Test(groups = {"group0"})
    public void JsonSchemaValidationTest() {
        given()
                .when()
                .get("https://bookstore.toolsqa.com/BookStore/v1/Books")
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("be.cbtw.apiTest.schema/list-book-schema-validator.json"))
                .log();

    }

    @Test(groups = {"group0"})
    public void xmlPathTest(){
       String responseApi= given()
                .accept(ContentType.XML)
                .when()
                .get("https://run.mocky.io/v3/42489e6b-edd0-460f-b075-ea3e9d9a282d")
                .thenReturn()
               .asString();
        XmlPath responseXmlPath =new  XmlPath(responseApi);
        Assert.assertEquals(responseXmlPath.getString("root.posts.id"),"1");
       Assert.assertEquals(responseXmlPath.getString("root.posts.body"),"This is the body of mock post 1");
        Assert.assertEquals(responseXmlPath.getString("root.posts.author.email"),"john.doe@example.com");
    }




}
