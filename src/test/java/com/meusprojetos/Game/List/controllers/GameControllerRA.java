package com.meusprojetos.Game.List.controllers;

import com.meusprojetos.Game.List.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class GameControllerRA {

    private Long existingGameId, nonExistingGameId, dependentGameId;
    private String clientToken, adminToken, invalidToken;
    private String clientUsername, adminUsername, clientPassword, adminPassword;

    private Map<String, Object> postGameInstance;
    private Map<String, Object> putGameInstance;

    @BeforeEach
    void setUp() throws Exception {

        baseURI = "http://localhost:8080";

        existingGameId = 1L;
        nonExistingGameId = 19L;
        dependentGameId = 11L;

        clientUsername = "amanda@gmail.com";
        clientPassword = "123456";

        adminUsername = "otavio@gmail.com";
        adminPassword = "123456";

        postGameInstance = new HashMap<>();
        postGameInstance.put("titulo", "Novo Game");
        postGameInstance.put("lancamento", LocalDate.parse("2022-03-01"));
        postGameInstance.put("console", "Novo console");
        postGameInstance.put("pontuacao", 5);
        postGameInstance.put("imgUrl", "nova imagem do game");
        postGameInstance.put("descricaoLonga", "Descricao longa do novo game adicionado");

        putGameInstance = new HashMap<>();
        putGameInstance.put("titulo", "Game atualizado");
        putGameInstance.put("lancamento", LocalDate.parse("2022-03-01"));
        putGameInstance.put("console", "console atualizado");
        putGameInstance.put("pontuacao", 5);
        putGameInstance.put("imgUrl", "nova imagem do game");
        putGameInstance.put("descricaoLonga", "Descricao longa do novo game atualizado");

        List<Map<String, Object>> genero = new ArrayList<>();

        Map<String, Object> genero1 = new HashMap<>();
        genero1.put("id", 3);
        Map<String, Object> genero2 = new HashMap<>();
        genero2.put("id", 8);

        genero.add(genero1);
        genero.add(genero2);

        postGameInstance.put("genero", genero);
        putGameInstance.put("genero", genero);

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";
    }

    @Test
    public void findByIdShouldReturnGameDTOWhenIdExists() {

        existingGameId = 2L;

        given()
                .get("/games/{id}", existingGameId)
                .then()
                .statusCode(200)
                .body("id", is(2))
                .body("titulo", equalTo("Red Dead Redemption 2"))
                .body("lancamento", equalTo("26-10-2018"))
                .body("console", equalTo("Playstation, XBox, PC"))
                .body("imgUrl", equalTo("https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/2.png"))
                .body("genero.nome", hasItems("Ação e Aventura", "RPG"));
    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExist() {

        nonExistingGameId = 100L;

        given()
                .get("games/{id}", nonExistingGameId)
                .then()
                .statusCode(404)
                .body("status", is(404))
                .body("error", equalTo("Id não encontrado."))
                .body("path", equalTo("/games/100"));
    }

    @Test
    public void findAllShouldReturnPageGamesWhenGameTitleIsEmpty() {

        given()
                .get("/games?page=0")
                .then()
                .statusCode(200)
                .body("content.titulo", hasItems("The Witcher 3: Wild Hunt","Ghost of Tsushima" ));
    }

    @Test
    public void findAllShouldReturnPageGamesWhenGameTitleIsNotEmpty() {

        given()
                .get("games?name=the witcher")
                .then()
                .statusCode(200)
                .body("content.titulo[0]", equalTo("The Witcher 3: Wild Hunt"))
                .body("content.id[0]", is(3));
    }

    @Test
    public void findAllShouldReturnPageGamesWithIdGreaterThan5() {

        given()
                .get("/games?size=15")
                .then()
                .statusCode(200)
                .body("content.findAll {it.id>5}.titulo", hasItems("Sonic CD", "Resident Evil 2 Remake"));
    }

    @Test
    public void insertShouldReturnGameCreateWhenAdminOrClientLogged() throws Exception {

        JSONObject newGame = new JSONObject(postGameInstance);

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newGame)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/games")
                .then()
                .statusCode(201)
                .body("titulo", equalTo("Novo Game"))
                .body("lancamento", equalTo("01-03-2022"))
                .body("console", equalTo("Novo console"))
                .body("pontuacao", is(5))
                .body("imgUrl", equalTo("nova imagem do game"))
                .body("descricaoLonga", equalTo("Descricao longa do novo game adicionado"))
                .body("genero.id", hasItems(3, 8));
    }
    @Test
    public void insertShouldThrowsUnprocessableEntityWhenScoreIsNegative() throws Exception {

        postGameInstance.put("pontuacao", -5);
        JSONObject newGame = new JSONObject(postGameInstance);

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newGame)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/games")
                .then()
                .statusCode(422)
                .body("error", equalTo("Dados inválidos!"))
                .body("errors.fieldName[0]", equalTo("pontuacao"))
                .body("errors.message[0]", equalTo("A pontuação dever ser maior que zero"));
    }

    @Test
    public void insertShouldThrowsUnauthorizedWhenLoggedInvalidToken() throws Exception {

        JSONObject newGame = new JSONObject(postGameInstance);

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + invalidToken)
                .body(newGame)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/games")
                .then()
                .statusCode(401);
    }

    @Test
    public void insertShouldThrowsUnprocessableEntityWhenGameTitleIsNullAndAdminOrClientLogged() throws Exception {

        postGameInstance.put("titulo", null);
        JSONObject newGame = new JSONObject(postGameInstance);

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newGame)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/games")
                .then()
                .statusCode(422)
                .body("error", equalTo("Dados inválidos!"))
                .body("path", equalTo("/games"))
                .body("errors.fieldName[0]", equalTo("titulo"))
                .body("errors.message[0]", equalTo("Campo obrigatório!"));
    }

    @Test
    public void updateShouldReturnGameWhenIdExistsAndAdminOrClientLogged() {

        JSONObject updateGame = new JSONObject(putGameInstance);

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(updateGame)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/games/{id}", existingGameId)
                .then()
                .statusCode(200)
                .body("titulo", equalTo("Game atualizado"))
                .body("lancamento", equalTo("01-03-2022"))
                .body("console", equalTo("console atualizado"))
                .body("pontuacao", is(5))
                .body("imgUrl", equalTo("nova imagem do game"))
                .body("descricaoLonga", equalTo("Descricao longa do novo game atualizado"))
                .body("genero.id", hasItems(3, 8));
    }

    @Test
    public void updateShouldThrowsUnauthorizedWhenLoggedInvalidTokenAndIdExists() throws Exception {

        JSONObject updateGame = new JSONObject(putGameInstance);

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + invalidToken)
                .body(updateGame)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/games/{id}", existingGameId)
                .then()
                .statusCode(401);
    }

    @Test
    public void updateShouldThrowsNotFoundExceptionWhenIdDoesNotExistAndAdminOrClientLogged() throws Exception {

        JSONObject updateGame = new JSONObject(postGameInstance);

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(updateGame)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/games/{id}", nonExistingGameId)
                .then()
                .statusCode(404)
                .body("error", equalTo("Id não encontrado."))
                .body("path", equalTo("/games/19"));
    }

    @Test
    public void updateShouldThrowsUnprocessableEntityWhenIdExistsAndNameIsNullAndAminOrClientLogged() throws Exception {

        putGameInstance.put("titulo", null);
        JSONObject updateGame = new JSONObject(putGameInstance);

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(updateGame)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/games/{id}", existingGameId)
                .then()
                .statusCode(422)
                .body("error", equalTo("Dados inválidos!"))
                .body("path", equalTo("/games/1"))
                .body("errors.fieldName[0]", equalTo("titulo"))
                .body("errors.message[0]", equalTo("Campo obrigatório!"));
    }

    @Test
    public void updateShouldThrowsUnprocessableEntityWhenScoreIsNegative() throws Exception {

        putGameInstance.put("pontuacao", -5);
        JSONObject updateGame = new JSONObject(putGameInstance);

        given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(updateGame)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/games/{id}", existingGameId)
                .then()
                .statusCode(422)
                .body("error", equalTo("Dados inválidos!"))
                .body("errors.fieldName[0]", equalTo("pontuacao"))
                .body("errors.message[0]", equalTo("A pontuação dever ser maior que zero"));
    }

    @Test
    public void deleteShouldReturnNotContentWhenIdExistsAndAdminOrClientLogged() throws Exception {

        existingGameId = 16L;

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .delete("/games/{id}", existingGameId)
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteShouldThrowsNotFoundExceptionWhenIdDoesNotExistAndAdminOrClientLogged() throws Exception {

        nonExistingGameId = 100L;

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .delete("/games/{id}", nonExistingGameId)
                .then()
                .statusCode(404)
                .body("error", equalTo("Id não encontrado"));
    }

    @Test
    public void deleteShouldThrowsBadRequestWhenAdminOrClientLoggedAndDependentId() throws Exception {

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .delete("/games/{id}", dependentGameId)
                .then()
                .statusCode(400)
                .body("error", equalTo("Falha de integridade referencial"));
    }

    @Test
    public void deleteShouldThrowsUnauthorizedWhenIdExistsAndInvalidToken() throws Exception {

        given()
                .header("Authorization", "Bearer " + invalidToken)
                .when()
                .delete("/games/{id}", existingGameId)
                .then()
                .statusCode(401);
    }

}
