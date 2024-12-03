package br.com.fiap.grupo30.fastfood.customer_api.e2e;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CreateNewCustomerE2ETest {
    @LocalServerPort private int port;

    @Test
    void test_ShouldReturnCustomer_WhenCreatingNewCustomer() {
        String cpf = "00615393098";
        RestAssured.baseURI = "http://localhost:" + port;

        given().when().get("/customers?cpf=" + cpf).then().statusCode(404);

        given().contentType("application/json")
                .body(
                        "{ \"name\": \"João\", \"cpf\": \""
                                + cpf
                                + "\", \"email\": \"joaozinho@example.com\" }")
                .when()
                .post("/customers")
                .then()
                .statusCode(201)
                .body("name", is("João"))
                .body("cpf", is(cpf))
                .body("email", is("joaozinho@example.com"));

        given().when()
                .get("/customers?cpf=" + cpf)
                .then()
                .statusCode(200)
                .body("name", is("João"))
                .body("cpf", is(cpf))
                .body("email", is("joaozinho@example.com"));

        // if it runs this, it means all checks above already passed
        var allChecksPasssed = true;
        assertTrue(allChecksPasssed, "All checks should pass");
    }
}
