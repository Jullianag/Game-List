package com.meusprojetos.Game.List.repositories;

import com.meusprojetos.Game.List.entities.Game;
import com.meusprojetos.Game.List.projections.GameMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT obj " +
            "FROM Game obj " +
            "WHERE UPPER(obj.titulo) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<Game> searchByTitulo(String name);

    @Query(nativeQuery = true, value = """
		SELECT tb_game.id, tb_game.titulo, tb_game.lancamento AS lancamento, tb_game.console AS console,
		tb_game.descricao_curta AS descricaoCurta, tb_ordenacao.posicao
		FROM tb_game
		INNER JOIN tb_ordenacao ON tb_game.id = tb_ordenacao.game_id
		WHERE tb_ordenacao.lista_id = :idList
		ORDER BY tb_ordenacao.posicao
			""")
    List<GameMinProjection> searchByList(Long idList);
}
