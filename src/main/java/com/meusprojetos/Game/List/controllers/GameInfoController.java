package com.meusprojetos.Game.List.controllers;

import com.meusprojetos.Game.List.dto.GameInfoDTO;
import com.meusprojetos.Game.List.services.GameInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/gameinfo")
public class GameInfoController {

    @Autowired
    private GameInfoService gameInfoService;

    @GetMapping
    public List<GameInfoDTO> findAll() {
        List<GameInfoDTO> result = gameInfoService.findAll();
        return result;
    }
}
