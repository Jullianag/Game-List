package com.meusprojetos.Game.List.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tb_game_info")
public class GameInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    // lista é o nome dado no Set de game
    @ManyToMany(mappedBy = "lista")
    private Set<Game> games = new HashSet<>();


    public GameInfo() {
    }

    public GameInfo(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Set<Game> getGames() {
        return games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameInfo gameInfo = (GameInfo) o;

        return Objects.equals(id, gameInfo.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
