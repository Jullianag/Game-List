package com.meusprojetos.Game.List.controllers;

import com.meusprojetos.Game.List.dto.GameDTO;
import com.meusprojetos.Game.List.dto.GameMinDTO;
import com.meusprojetos.Game.List.services.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<GameDTO> insert(@Valid @RequestBody GameDTO dto) {
        dto = gameService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<GameDTO> update(@PathVariable Long id, @Valid @RequestBody GameDTO dto) {
        dto = gameService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
