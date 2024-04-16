package com.meusprojetos.Game.List.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public class PertencePK {

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "lista_id")
    private GameInfo lista;

    public PertencePK() {
    }

    public PertencePK(Game game, GameInfo lista) {
        this.game = game;
        this.lista = lista;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameInfo getLista() {
        return lista;
    }

    public void setLista(GameInfo lista) {
        this.lista = lista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PertencePK that = (PertencePK) o;

        if (!Objects.equals(game, that.game)) return false;
        return Objects.equals(lista, that.lista);
    }

    @Override
    public int hashCode() {
        int result = game != null ? game.hashCode() : 0;
        result = 31 * result + (lista != null ? lista.hashCode() : 0);
        return result;
    }
}
