package com.meusprojetos.Game.List.repositories;

import com.meusprojetos.Game.List.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface GameRepository extends JpaRepository<Game, Long> {
}
