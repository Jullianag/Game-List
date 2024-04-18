package com.meusprojetos.Game.List.projections;

import java.time.LocalDate;

public interface GameMinProjection {

    Long getId();

    String getTitulo();

    LocalDate getLancamento();

    String getConsole();

    String getDescricaoCurta();

    Integer getPosicao();
}
