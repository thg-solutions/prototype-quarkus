package de.thg.prototype;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public
class PeopleResourceTest {

    @Test
    void findAll() {
        given()
                .when().get("/people")
                .then()
                .statusCode(200)
                .body(containsString("Bond"),
                        containsString("Bourne"),
                        containsString("Bauer"));
    }

    @Test
    void findById() {
        given()
                .when().get("/people/2")
                .then()
                .statusCode(200)
                .body("lastname", equalTo("Bond"));
    }

    @Test
    void findByLastname() {
        given()
                .when().get("/people/search/findByLastname?name=Bond")
                .then()
                .statusCode(200)
                .body(containsString("James"));
    }

    @Test
    void findByFirstname() {
        given()
                .when().get("/people/search/findByFirstname?name=Jason")
                .then()
                .statusCode(200)
                .body("[0].firstname", equalTo("Jason"));
    }

    @Test
    void findByLastnameStartingWith() {
        given()
                .when().get("/people/search/findByLastnameStartingWith?abbr=B")
                .then()
                .statusCode(200)
                .body(containsString("Bond"),
                        containsString("Bourne"),
                        containsString("Bauer"));

    }

    @Test
    void notFound() {
        given()
                .when().get("/not-found")
                .then().statusCode(404);
    }

}