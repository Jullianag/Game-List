package com.meusprojetos.Game.List.services;

import com.meusprojetos.Game.List.dto.GameDTO;
import com.meusprojetos.Game.List.dto.GameInfoDTO;
import com.meusprojetos.Game.List.dto.GameMinDTO;
import com.meusprojetos.Game.List.entities.Game;
import com.meusprojetos.Game.List.entities.GameInfo;
import com.meusprojetos.Game.List.projections.GameMinProjection;
import com.meusprojetos.Game.List.repositories.GameRepository;
import com.meusprojetos.Game.List.services.exceptions.DatabaseException;
import com.meusprojetos.Game.List.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Game result = gameRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado.")
        );
        return new GameDTO(result);
    }

    @Transactional(readOnly = true)
    public Page<GameMinDTO> findAll(String titulo, Pageable pageable) {
        Page<Game> result = gameRepository.searchByTitulo(titulo, pageable);
        return result.map(x -> new GameMinDTO(x));
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

        try {
            Game entity = gameRepository.getReferenceById(id);
            copyDTOToEntity(dto, entity);
            entity = gameRepository.save(entity);
            return new GameDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado.");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id não encontrado");
        }
        try {
            gameRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
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
