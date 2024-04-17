package com.meusprojetos.Game.List.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private LocalDate lancamento;
    private String console;
    private Integer pontuacao;
    private String imgUrl;
    @Column(columnDefinition = "TEXT")
    private String descricaoCurta;
    @Column(columnDefinition = "TEXT")
    private String descricaoLonga;


    @ManyToMany
    @JoinTable(name = "tb_game_game_info",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "lista_id"))
    private Set<GameInfo> lista = new HashSet<>();


    public Game() {
    }

    public Game(Long id, String titulo, LocalDate lancamento, String console,
                Integer pontuacao, String imgUrl, String descricaoCurta,
                String descricaoLonga) {
        this.id = id;
        this.titulo = titulo;
        this.lancamento = lancamento;
        this.console = console;
        this.pontuacao = pontuacao;
        this.imgUrl = imgUrl;
        this.descricaoCurta = descricaoCurta;
        this.descricaoLonga = descricaoLonga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getLancamento() {
        return lancamento;
    }

    public void setLancamento(LocalDate lancamento) {
        this.lancamento = lancamento;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public String getDescricaoLonga() {
        return descricaoLonga;
    }

    public void setDescricaoLonga(String descricaoLonga) {
        this.descricaoLonga = descricaoLonga;
    }

    public Set<GameInfo> getLista() {
        return lista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
