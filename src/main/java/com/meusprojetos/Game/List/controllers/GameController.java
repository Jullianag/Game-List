package com.meusprojetos.Game.List.controllers;

import com.meusprojetos.Game.List.dto.GameDTO;
import com.meusprojetos.Game.List.dto.GameMinDTO;
import com.meusprojetos.Game.List.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<GameDTO> findById(@PathVariable Long id) {
        GameDTO result = gameService.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Page<GameMinDTO>> findAll(
            @RequestParam(name = "name", defaultValue = "") String titulo,
            Pageable pageable) {
        Page<GameMinDTO> result = gameService.findAll(titulo, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<GameDTO> insert(@RequestBody GameDTO dto) {
        dto = gameService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GameDTO> update(@PathVariable Long id, @RequestBody GameDTO dto) {
        dto = gameService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
