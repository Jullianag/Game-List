package com.meusprojetos.Game.List.controllers;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import static io.restassured.RestAssured.baseURI;

public class GameInfoControllerRA {

    private String gameIfoName;
    private Long existingGameInfoId, nonExistingGameInfoId, idList;

    private Map<String, Object> postTroca;

    @BeforeEach
    void setUp() throws Exception {

        baseURI = "http://localhost:8080";

        gameIfoName = "RPG";
        existingGameInfoId = 3L;
        nonExistingGameInfoId = 9L;
        idList = 1L;

        postTroca = new HashMap<>();
        postTroca.put("primeiroIndex", 1);
        postTroca.put("segundoIndex", 3);
    }

    @Test
    public void findAllShouldReturnPageGameInfoWhenGameInfoIsEmpty() {

        given()
                .get("/gameinfo?page=0")
                .then()
                .statusCode(200)
                .body("content.id", hasItems(1, 2, 3, 8))
                .body("content.nome", hasItems("RPG", "Tiro", "Estratégia"));

    }

    @Test
    public void findAllShouldReturnPageGameInfoWhenGameInfoIsNotEmpty() {

        given()
                .get("/gameinfo?name={gameInfoName}", gameIfoName)
                .then()
                .statusCode(200)
                .body("content.id[0]", is(3))
                .body("content.nome[0]", equalTo("RPG"));
    }

    @Test
    public void findByListShouldReturnListOfGameMinDTOWhenIdList() {

        given()
                .get("/gameinfo/{idList}/games", idList)
                .then()
                .statusCode(200)
                .body("titulo", hasItems( "Mass Effect Trilogy", "The Witcher 3: Wild Hunt"));
    }

    @Test
    public void moveShouldReturnNoContent() {

        JSONObject troca = new JSONObject(postTroca);

        given()
                .header("content-type", "application/json")
                .body(troca)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/gameinfo/{idList}/troca", idList)
                .then()
                .statusCode(204);
    }
}
