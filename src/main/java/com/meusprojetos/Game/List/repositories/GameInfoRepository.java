package com.meusprojetos.Game.List.repositories;

import com.meusprojetos.Game.List.entities.GameInfo;
import com.meusprojetos.Game.List.entities.Ordenacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE tb_ordenacao SET posicao = :newPosition WHERE lista_id = :listId AND game_id = :gameId")
    void updateBelongingPosition(Long listId, Long gameId, Integer newPosition);

    @Query("SELECT obj " +
            "FROM GameInfo obj " +
            "WHERE UPPER(obj.nome) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<GameInfo> searchByNome(String name, Pageable pageable);

}
