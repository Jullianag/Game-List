package com.meusprojetos.Game.List.tests;

import com.meusprojetos.Game.List.config.JacksonLocalDateConfigurations;
import com.meusprojetos.Game.List.entities.Game;
import com.meusprojetos.Game.List.entities.GameInfo;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;

import java.time.LocalDate;

public class GameFactory {

    public static Game createGame() {

        GameInfo gameInfo = GameInfoFactory.createGameInfo();
        Game game = new Game(1L, "Final Fantasy 7 Rebirth", LocalDate.parse("2024-02-29"), "Playstation", 5, "https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/10.png",
                "Final Fantasy VII Rebirth é um jogo eletrônico de RPG de ação desenvolvido e publicado pela Square Enix.", "É um título derivado da franquia Final Fantasy, uma recriação de Final Fantasy VII de 1997 e uma sequência de Final Fantasy VII Remake de 2020, tendo sido lançado mundialmente em 29 de fevereiro de 2024 para PlayStation 5.");
        game.getLista().add(gameInfo);
        return game;
    }

    public static Game createGame(String titulo) {

        Game game = createGame();
        game.setTitulo(titulo);
        return game;
    }

}
