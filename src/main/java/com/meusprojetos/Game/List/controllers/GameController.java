package com.meusprojetos.Game.List.controllers;

import com.meusprojetos.Game.List.dto.GameDTO;
import com.meusprojetos.Game.List.dto.GameMinDTO;
import com.meusprojetos.Game.List.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping(value = "/{id}")
    public GameDTO findById(@PathVariable Long id) {
        GameDTO result = gameService.findById(id);
        return result;
    }

    @GetMapping
    public List<GameMinDTO> findAll(
            @RequestParam(name = "name", defaultValue = "") String titulo) {
        List<GameMinDTO> result = gameService.findAll(titulo);
        return result;
    }

    @PostMapping
    public GameDTO insert(@RequestBody GameDTO dto) {
        dto = gameService.insert(dto);
        return dto;
    }

    @PutMapping(value = "/{id}")
    public GameDTO update(@PathVariable Long id, @RequestBody GameDTO dto) {
        dto = gameService.update(id, dto);
        return dto;
    }

    @DeleteMapping(value = "/{id}")
    public Void delete(@PathVariable Long id) {
        gameService.delete(id);
        return null;
    }
}
