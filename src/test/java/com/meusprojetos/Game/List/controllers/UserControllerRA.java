package com.meusprojetos.Game.List.controllers;

import com.meusprojetos.Game.List.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class UserControllerRA {

    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String adminToken, clientToken, invalidToken;

    private Long existingUserId, nonExistingUserId, dependentUserId;

    @BeforeEach
    public void setUp()  throws Exception {

        baseURI = "http://localhost:8080";

        clientUsername = "amanda@gmail.com";
        clientPassword = "123456";

        adminUsername = "otavio@gmail.com";
        adminPassword = "123456";

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";

    }

    @Test
    public void getMeShouldReturnUserWhenAdminLogged() throws Exception {

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ContentType.JSON)
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .body("id", is(2))
                .body("nome", equalTo("Otavio"))
                .body("email", equalTo("otavio@gmail.com"))
                .body("roles", hasItems("ROLE_CLIENT", "ROLE_ADMIN"));
    }

    @Test
    public void getMeShouldReturnUserWhenClientLogged() throws Exception {

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + clientToken)
                .accept(ContentType.JSON)
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("nome", equalTo("Amanda"))
                .body("email", equalTo("amanda@gmail.com"))
                .body("roles", hasItems("ROLE_CLIENT"));
    }

    @Test
    public void getMeShouldReturnUnauthorizedWhenInvalidToken() throws Exception {

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + invalidToken)
                .accept(ContentType.JSON)
                .when()
                .get("/users/me")
                .then()
                .statusCode(401);
    }
}
