package com.meusprojetos.Game.List.dto;

import com.meusprojetos.Game.List.entities.Game;
import com.meusprojetos.Game.List.entities.Ordenacao;
import com.meusprojetos.Game.List.projections.GameMinProjection;

import java.time.LocalDate;
import java.util.*;

public class GameMinDTO {

    private Long id;
    private String titulo;
    private LocalDate lancamento;
    private String console;
    private String descricaoCurta;

    private Set<OrdenacaoDTO> index = new HashSet<>();

    public GameMinDTO() {
    }


    public GameMinDTO(Long id, String titulo, LocalDate lancamento, String console, String descricaoCurta) {
        this.id = id;
        this.titulo = titulo;
        this.lancamento = lancamento;
        this.console = console;
        this.descricaoCurta = descricaoCurta;
    }

    public GameMinDTO(Game entity) {
        id = entity.getId();
        titulo = entity.getTitulo();
        lancamento = entity.getLancamento();
        console = entity.getConsole();
        descricaoCurta = entity.getDescricaoCurta();
        for (Ordenacao ordenacao : entity.getItems()) {
            if (index == getIndex()) {
                index.removeIf(x -> Objects.equals(x.getPosicao(), x.getPosicao()));
                index.add(new OrdenacaoDTO(ordenacao));
            }
        }
    }

    public GameMinDTO(GameMinProjection projection) {
        id = projection.getId();
        titulo = projection.getTitulo();
        lancamento = projection.getLancamento();
        console = projection.getConsole();
        descricaoCurta = projection.getDescricaoCurta();
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

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public Set<OrdenacaoDTO> getIndex() {
        return index;
    }
}
