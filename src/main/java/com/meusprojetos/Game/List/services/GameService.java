package com.meusprojetos.Game.List.services;

import com.meusprojetos.Game.List.dto.GameDTO;
import com.meusprojetos.Game.List.dto.GameInfoDTO;
import com.meusprojetos.Game.List.dto.GameMinDTO;
import com.meusprojetos.Game.List.entities.Game;
import com.meusprojetos.Game.List.entities.GameInfo;
import com.meusprojetos.Game.List.projections.GameMinProjection;
import com.meusprojetos.Game.List.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;


    @Transactional(readOnly = true)
    public GameDTO findById(Long id) {
        Game result = gameRepository.findById(id).get();
        return new GameDTO(result);
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll(String titulo) {
        List<Game> result = gameRepository.searchByTitulo(titulo);
        return result.stream().map(x -> new GameMinDTO(x)).toList();
    }

    @Transactional
    public GameDTO insert(GameDTO dto) {
        Game entity = new Game();
        copyDTOToEntity(dto, entity);
        entity = gameRepository.save(entity);
        return new GameDTO(entity);
    }

    @Transactional
    public GameDTO update(Long id, GameDTO dto) {
        Game entity = gameRepository.getReferenceById(id);
        copyDTOToEntity(dto, entity);
        entity = gameRepository.save(entity);
        return new GameDTO(entity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByList(Long listId) {
        List<GameMinProjection> result = gameRepository.searchByList(listId);
        return result.stream().map(x -> new GameMinDTO(x)).toList();
    }

    private void copyDTOToEntity(GameDTO dto, Game entity) {
        entity.setTitulo(dto.getTitulo());
        entity.setLancamento(dto.getLancamento());
        entity.setConsole(dto.getConsole());
        entity.setPontuacao(dto.getPontuacao());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDescricaoLonga(dto.getDescricaoLonga());

        entity.getLista().clear();
        for (GameInfoDTO gameInfoDTO : dto.getGenero()) {
            GameInfo gameInfo = new GameInfo();
            gameInfo.setId(gameInfoDTO.getId());
            entity.getLista().add(gameInfo);
        }
    }
}
