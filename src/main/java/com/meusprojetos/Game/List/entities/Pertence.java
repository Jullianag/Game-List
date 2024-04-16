package com.meusprojetos.Game.List.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "tb_pertence")
public class Pertence {

    @EmbeddedId
    private PertencePK id = new PertencePK();
    private Integer posicao;

    public Pertence() {
    }

    public Pertence(Game game, GameInfo lista, Integer posicao) {
        id.setGame(game);
        id.setLista(lista);
        this.posicao = posicao;
    }

    public Game getGame() {
        return id.getGame();
    }

    public void setGame(Game game) {
        id.setGame(game);
    }

    public GameInfo getGameInfo() {
        return id.getLista();
    }

    public void setGameInfo(GameInfo lista) {
        id.setLista(lista);
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pertence pertence = (Pertence) o;

        return Objects.equals(id, pertence.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
