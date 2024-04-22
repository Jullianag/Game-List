package com.meusprojetos.Game.List.repositories;

import com.meusprojetos.Game.List.entities.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE tb_ordenacao SET posicao = :newPosition WHERE lista_id = :listId AND game_id = :gameId")
    void updateBelongingPosition(Long listId, Long gameId, Integer newPosition);

}
