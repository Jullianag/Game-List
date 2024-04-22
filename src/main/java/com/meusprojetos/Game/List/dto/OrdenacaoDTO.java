package com.meusprojetos.Game.List.dto;

import com.meusprojetos.Game.List.entities.Game;
import com.meusprojetos.Game.List.entities.Ordenacao;
import com.meusprojetos.Game.List.entities.OrdenacaoPK;

public class OrdenacaoDTO {

    private Integer posicao;

    public OrdenacaoDTO() {
    }

    public OrdenacaoDTO(Integer posicao) {
        this.posicao = posicao;
    }

    public OrdenacaoDTO(Ordenacao entity) {
        posicao = entity.getPosicao();
    }

    public Integer getPosicao() {
        return posicao;
    }

}
