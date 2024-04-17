package com.meusprojetos.Game.List.services;

import com.meusprojetos.Game.List.dto.GameInfoDTO;
import com.meusprojetos.Game.List.entities.GameInfo;
import com.meusprojetos.Game.List.repositories.GameInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameInfoService {

    @Autowired
    private GameInfoRepository gameInfoRepository;

    @Transactional(readOnly = true)
    public List<GameInfoDTO> findAll() {
        List<GameInfo> result = gameInfoRepository.findAll();
        return result.stream().map(x -> new GameInfoDTO(x)).toList();
    }
}
