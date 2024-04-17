package com.meusprojetos.Game.List.dto;

import com.meusprojetos.Game.List.entities.Game;

import java.time.LocalDate;

public class GameDTO {

    private Long id;
    private String titulo;
    private LocalDate lancamento;
    private String console;
    private Integer pontuacao;
    private String imgUrl;
    private String descricaoCurta;
    private String descricaoLonga;

    public GameDTO(Long id, String titulo, LocalDate lancamento, String console, Integer pontuacao,
                   String imgUrl, String descricaoCurta, String descricaoLonga) {
        this.id = id;
        this.titulo = titulo;
        this.lancamento = lancamento;
        this.console = console;
        this.pontuacao = pontuacao;
        this.imgUrl = imgUrl;
        this.descricaoCurta = descricaoCurta;
        this.descricaoLonga = descricaoLonga;
    }

    public GameDTO(Game entity) {
        id = entity.getId();
        titulo = entity.getTitulo();
        lancamento = entity.getLancamento();
        console = entity.getConsole();
        pontuacao = entity.getPontuacao();
        imgUrl = entity.getImgUrl();
        descricaoCurta = entity.getDescricaoCurta();
        descricaoLonga = entity.getDescricaoLonga();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDate getLancamento() {
        return lancamento;
    }

    public String getConsole() {
        return console;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public String getDescricaoLonga() {
        return descricaoLonga;
    }
}
