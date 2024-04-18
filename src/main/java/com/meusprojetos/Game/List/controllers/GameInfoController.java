package com.meusprojetos.Game.List.controllers;

import com.meusprojetos.Game.List.dto.GameInfoDTO;
import com.meusprojetos.Game.List.dto.GameMinDTO;
import com.meusprojetos.Game.List.services.GameInfoService;
import com.meusprojetos.Game.List.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/gameinfo")
public class GameInfoController {

    @Autowired
    private GameInfoService gameInfoService;

    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameInfoDTO> findAll() {
        List<GameInfoDTO> result = gameInfoService.findAll();
        return result;
    }

    @GetMapping(value = "/{idLista}/games")
    public List<GameMinDTO> findByList(@PathVariable Long idLista) {
        List<GameMinDTO> result = gameService.findByList(idLista);
        return result;
    }
}
