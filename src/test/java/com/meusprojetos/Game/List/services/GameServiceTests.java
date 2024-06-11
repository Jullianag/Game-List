package com.meusprojetos.Game.List.services;

import com.meusprojetos.Game.List.dto.GameDTO;
import com.meusprojetos.Game.List.dto.GameInfoDTO;
import com.meusprojetos.Game.List.dto.GameMinDTO;
import com.meusprojetos.Game.List.entities.Game;
import com.meusprojetos.Game.List.entities.GameInfo;
import com.meusprojetos.Game.List.projections.GameMinProjection;
import com.meusprojetos.Game.List.repositories.GameRepository;
import com.meusprojetos.Game.List.repositories.UserRepository;
import com.meusprojetos.Game.List.services.exceptions.DatabaseException;
import com.meusprojetos.Game.List.services.exceptions.ResourceNotFoundException;
import com.meusprojetos.Game.List.tests.GameFactory;
import com.meusprojetos.Game.List.tests.GameInfoFactory;
import com.meusprojetos.Game.List.tests.GameMinProjectionFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class GameServiceTests {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    private Game game;
    private GameDTO gameDTO;
    private PageImpl<Game> page;
    private GameMinDTO gameMinDTO;

    private Long existingGameId, nonExistingGameId, dependentGameId;

    private List<GameMinProjection> gameMinProjections;

    @BeforeEach
    public void setUp() throws Exception {

        existingGameId = 1L;
        nonExistingGameId = 2L;
        dependentGameId = 3L;

        game = GameFactory.createGame();
        gameDTO = new GameDTO(game);

        gameMinDTO = new GameMinDTO(1L, "Titulo", LocalDate.parse("2023-03-25"), "Playstation", "Descricao curta do jogo");

        gameMinProjections = GameMinProjectionFactory.createGameMinProjection();

        page = new PageImpl<>(List.of(game));

        Mockito.when(gameRepository.findById(existingGameId)).thenReturn(Optional.of(game));
        Mockito.when(gameRepository.findById(nonExistingGameId)).thenReturn(Optional.empty());

        Mockito.when(gameRepository.searchByTitulo(any(), (Pageable) Mockito.any())).thenReturn(page);

        Mockito.when(gameRepository.save(any())).thenReturn(game);

        Mockito.when(gameRepository.getReferenceById(existingGameId)).thenReturn(game);
        Mockito.when(gameRepository.getReferenceById(nonExistingGameId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(gameRepository.existsById(existingGameId)).thenReturn(true);
        Mockito.when(gameRepository.existsById(dependentGameId)).thenReturn(true);
        Mockito.when(gameRepository.existsById(nonExistingGameId)).thenReturn(false);

        Mockito.doNothing().when(gameRepository).deleteById(existingGameId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(gameRepository).deleteById(dependentGameId);

        Mockito.when(gameRepository.searchByList(existingGameId)).thenReturn(gameMinProjections);

    }

    @Test
    public void findByIdShouldReturnGameDTOWhenIdExists() {

        GameDTO result = gameService.findById(existingGameId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingGameId);
        Assertions.assertEquals(result.getTitulo(), game.getTitulo());
        Assertions.assertEquals(result.getConsole(), game.getConsole());
    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            gameService.findById(nonExistingGameId);
        });
    }

    @Test
    public void findAllShouldReturnPageGameMinDTO() {

        Pageable pageable = PageRequest.of(0, 12);
        Page<GameMinDTO> result = gameService.findAll(game.getTitulo(), pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getSize(), 1);
        Assertions.assertEquals(result.iterator().next().getTitulo(), game.getTitulo());
        Assertions.assertEquals(result.iterator().next().getConsole(), game.getConsole());
        Assertions.assertEquals(result.iterator().next().getId(), game.getId());

    }

    @Test
    public void insertShouldReturnGameDTO() {

        GameDTO result = gameService.insert(gameDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), gameDTO.getId());
        Assertions.assertEquals(result.getTitulo(), gameDTO.getTitulo());
        Assertions.assertEquals(result.getConsole(), gameDTO.getConsole());

    }

    @Test
    public void updateShouldReturnGameDTOWhenIdExists() {

        GameDTO result = gameService.update(existingGameId, gameDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingGameId);
        Assertions.assertEquals(result.getTitulo(), gameDTO.getTitulo());

    }

    @Test
    public void updateShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            gameService.update(nonExistingGameId, gameDTO);
        });
    }

    @Test
    public void deleteShouldReturnDoNothingWhenIdExists() {

        Assertions.assertDoesNotThrow(() -> {
            gameService.delete(existingGameId);
        });
    }

    @Test
    public void deleteShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            gameService.delete(nonExistingGameId);
        });
    }

    @Test
    public void deleteShouldThrowsDatabaseExceptionWhenDependentId() {

        Assertions.assertThrows(DatabaseException.class, () -> {
            gameService.delete(dependentGameId);
        });
    }

    @Test
    public void findByListShouldReturnListGameMinDTO() {

        List<GameMinDTO> result = gameService.findByList(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.iterator().next().getConsole(), gameMinProjections.iterator().next().getConsole());
    }

}
