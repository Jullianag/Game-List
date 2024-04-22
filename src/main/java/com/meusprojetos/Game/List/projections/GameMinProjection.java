package com.meusprojetos.Game.List.projections;

import com.meusprojetos.Game.List.dto.OrdenacaoDTO;

import java.time.LocalDate;
import java.util.Set;

public interface GameMinProjection {

    Long getId();

    String getTitulo();

    LocalDate getLancamento();

    String getConsole();

    String getDescricaoCurta();

    Set<OrdenacaoDTO> getPosicao();
}
