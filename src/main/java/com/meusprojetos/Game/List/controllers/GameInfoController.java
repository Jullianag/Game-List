package com.meusprojetos.Game.List.controllers;

import com.meusprojetos.Game.List.dto.GameInfoDTO;
import com.meusprojetos.Game.List.dto.GameMinDTO;
import com.meusprojetos.Game.List.dto.TrocarDTO;
import com.meusprojetos.Game.List.services.GameInfoService;
import com.meusprojetos.Game.List.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/gameinfo")
public class GameInfoController {

    @Autowired
    private GameInfoService gameInfoService;

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<Page<GameInfoDTO>> findAll(
            @RequestParam(name = "name", defaultValue = "") String nome,
            Pageable pageable)
    {
        Page<GameInfoDTO> result = gameInfoService.findAll(nome, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{idLista}/games")
    public ResponseEntity<List<GameMinDTO>> findByList(@PathVariable Long idLista) {
        List<GameMinDTO> result = gameService.findByList(idLista);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/{IdLista}/troca")
    public ResponseEntity<Void> move(@PathVariable Long IdLista, @RequestBody TrocarDTO body) {
        gameInfoService.move(IdLista, body.getPrimeiroIndex(), body.getSegundoIndex());
        return ResponseEntity.noContent().build();
    }
}
