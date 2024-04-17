package com.meusprojetos.Game.List.dto;

import com.meusprojetos.Game.List.entities.GameInfo;

public class GameInfoDTO {

    private Long id;
    private String nome;

    public GameInfoDTO() {
    }

    public GameInfoDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public GameInfoDTO(GameInfo entity) {
        id = entity.getId();
        nome = entity.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
