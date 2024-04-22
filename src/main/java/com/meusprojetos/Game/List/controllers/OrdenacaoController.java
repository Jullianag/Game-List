package com.meusprojetos.Game.List.controllers;

import com.meusprojetos.Game.List.dto.OrdenacaoDTO;
import com.meusprojetos.Game.List.services.OrdenacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ordenacao")
public class OrdenacaoController {

    @Autowired
    private OrdenacaoService ordenacaoService;

    @GetMapping
    public List<OrdenacaoDTO> findAll() {
        List<OrdenacaoDTO> resul = ordenacaoService.findAll();
        return resul;
    }
}
