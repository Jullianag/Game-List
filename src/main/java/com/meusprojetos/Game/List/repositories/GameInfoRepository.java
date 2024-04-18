package com.meusprojetos.Game.List.repositories;

import com.meusprojetos.Game.List.entities.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {

}
