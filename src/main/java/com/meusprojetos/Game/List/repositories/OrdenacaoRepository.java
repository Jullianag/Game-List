package com.meusprojetos.Game.List.repositories;

import com.meusprojetos.Game.List.entities.Ordenacao;
import com.meusprojetos.Game.List.entities.OrdenacaoPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenacaoRepository extends JpaRepository<Ordenacao, OrdenacaoPK> {
}
