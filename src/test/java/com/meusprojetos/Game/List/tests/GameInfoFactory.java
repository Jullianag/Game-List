package com.meusprojetos.Game.List.tests;

import com.meusprojetos.Game.List.entities.GameInfo;

public class GameInfoFactory {

    public static GameInfo createGameInfo() {

        return new GameInfo(9L, "Novo genero");
    }

    public static GameInfo createGameInfo(Long id, String name) {

        return new GameInfo(id, name);
    }
}
