package com.meusprojetos.Game.List.tests;

import com.meusprojetos.Game.List.dto.OrdenacaoDTO;
import com.meusprojetos.Game.List.projections.GameMinProjection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameMinProjectionFactory {

    public static List<GameMinProjection> createGameMinProjection() {

        List<GameMinProjection> list = new ArrayList<>();
        list.add(new GameMinProjection() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getTitulo() {
                return "Titulo da projection";
            }

            @Override
            public LocalDate getLancamento() {
                return LocalDate.parse("2001-12-20");
            }

            @Override
            public String getConsole() {
                return "Playstation";
            }

            @Override
            public String getDescricaoCurta() {
                return "Descricao curta da projection";
            }

            @Override
            public Set<OrdenacaoDTO> getPosicao() {
                return Set.of();
            }
        });
        return list;
    }
}
