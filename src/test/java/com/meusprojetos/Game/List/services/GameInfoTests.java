package com.meusprojetos.Game.List.services;

import com.meusprojetos.Game.List.dto.GameInfoDTO;
import com.meusprojetos.Game.List.entities.GameInfo;
import com.meusprojetos.Game.List.projections.GameMinProjection;
import com.meusprojetos.Game.List.repositories.GameInfoRepository;
import com.meusprojetos.Game.List.repositories.GameRepository;
import com.meusprojetos.Game.List.tests.GameInfoFactory;
import com.meusprojetos.Game.List.tests.GameMinProjectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class GameInfoTests {

    @InjectMocks
    private GameInfoService gameInfoService;

    @Mock
    private GameInfoRepository gameInfoRepository;

    @Mock
    private GameRepository gameRepository;

    private GameInfo gameInfo;
    private GameInfoDTO gameInfoDTO;
    private PageImpl<GameInfo> page;

    private List<GameMinProjection> gameMinProjections;

    private Long existingGameInfoId, nonExistingGameInfoId;

    @BeforeEach
    void setUp() throws Exception {

        existingGameInfoId = 1L;
        nonExistingGameInfoId = 2L;

        gameInfo = GameInfoFactory.createGameInfo();
        gameInfoDTO = new GameInfoDTO(gameInfo);

        page = new PageImpl<>(List.of(gameInfo));

        gameMinProjections = GameMinProjectionFactory.createGameMinProjection();

        Mockito.when(gameInfoRepository.searchByNome(any(), (Pageable) any())).thenReturn(page);

    }

    @Test
    public void findAllShouldReturnPageGameInfoDTO() {

        Pageable pageable = PageRequest.of(0, 12);
        Page<GameInfoDTO> result = gameInfoService.findAll(gameInfo.getNome(), pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.iterator().next().getId(), gameInfo.getId());
    }

    @Test
    public void moveShouldReturnDoNothing() {

        Mockito.when(gameRepository.searchByList(2L)).thenReturn(gameMinProjections);
        Mockito.doNothing().when(gameInfoRepository).updateBelongingPosition(2L,1L, 2);



    }
}
