package com.meusprojetos.Game.List.dto;

import com.meusprojetos.Game.List.entities.Game;
import com.meusprojetos.Game.List.entities.GameInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameDTO {

    private Long id;
    @Size(min = 3, max = 100, message = "O título precisar ter entre 3 e 100 caracteres.")
    @NotBlank(message = "Campo obrigatório!")
    private String titulo;
    private LocalDate lancamento;
    private String console;
    @Positive(message = "A pontuação dever ser maior que zero")
    private Integer pontuacao;
    private String imgUrl;
    @Size(min = 20, message = "A descrição precisa ter no mínimo 20 caracteres.")
    @NotBlank(message = "Campo obrigatório")
    private String descricaoLonga;

    private List<GameInfoDTO> genero = new ArrayList<>();

    public GameDTO(Long id, String titulo, LocalDate lancamento, String console, Integer pontuacao,
                   String imgUrl, String descricaoLonga) {
        this.id = id;
        this.titulo = titulo;
        this.lancamento = lancamento;
        this.console = console;
        this.pontuacao = pontuacao;
        this.imgUrl = imgUrl;
        this.descricaoLonga = descricaoLonga;
    }

    public GameDTO(Game entity) {
        id = entity.getId();
        titulo = entity.getTitulo();
        lancamento = entity.getLancamento();
        console = entity.getConsole();
        pontuacao = entity.getPontuacao();
        imgUrl = entity.getImgUrl();
        descricaoLonga = entity.getDescricaoLonga();
        for (GameInfo gameInfo : entity.getLista()) {
            genero.add(new GameInfoDTO(gameInfo));
        }
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

    public String getDescricaoLonga() {
        return descricaoLonga;
    }

    public List<GameInfoDTO> getGenero() {
        return genero;
    }
}
