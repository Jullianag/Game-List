package com.meusprojetos.Game.List.services;

import com.meusprojetos.Game.List.dto.GameInfoDTO;
import com.meusprojetos.Game.List.entities.GameInfo;
import com.meusprojetos.Game.List.projections.GameMinProjection;
import com.meusprojetos.Game.List.repositories.GameInfoRepository;
import com.meusprojetos.Game.List.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameInfoService {

    @Autowired
    private GameInfoRepository gameInfoRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public Page<GameInfoDTO> findAll(String nome, Pageable pageable) {
        Page<GameInfo> result = gameInfoRepository.searchByNome(nome, pageable);
        return result.map(x -> new GameInfoDTO(x));
    }

    @Transactional
    public void move(Long listId, int primeiroIndex, int segundoIndex) {
        List<GameMinProjection> list = gameRepository.searchByList(listId);

        GameMinProjection obj = list.remove(primeiroIndex);
        list.add(segundoIndex, obj);

        int min = primeiroIndex < segundoIndex ? primeiroIndex : segundoIndex;
        int max = primeiroIndex < segundoIndex ? segundoIndex : primeiroIndex;

        for (int i = min; i <= max; i++) {
            gameInfoRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
        }
    }
}
