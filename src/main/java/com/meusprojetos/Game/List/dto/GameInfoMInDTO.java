package com.meusprojetos.Game.List.dto;

import com.meusprojetos.Game.List.entities.GameInfo;

public class GameInfoMInDTO {

    private String nome;

    public GameInfoMInDTO() {
    }

    public GameInfoMInDTO(String nome) {
        this.nome = nome;
    }

    public GameInfoMInDTO(GameInfo entity) {
        nome = entity.getNome();
    }

    public String getNome() {
        return nome;
    }
}
