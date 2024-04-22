package com.meusprojetos.Game.List.dto;

import com.meusprojetos.Game.List.entities.Game;
import com.meusprojetos.Game.List.entities.Ordenacao;
import com.meusprojetos.Game.List.entities.OrdenacaoPK;

public class OrdenacaoDTO {

    private Long gameId;
    private Integer posicao;

    public OrdenacaoDTO() {
    }

    public OrdenacaoDTO(Integer posicao, Long gameId) {
        this.gameId = gameId;
        this.posicao = posicao;
    }

    public OrdenacaoDTO(Ordenacao entity) {
        gameId = entity.getGame().getId();
        posicao = entity.getPosicao();
    }

    public Integer getPosicao() {
        return posicao;
    }

    public Long getGameId() {
        return gameId;
    }
}
