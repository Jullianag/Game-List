package com.meusprojetos.Game.List.services;

import com.meusprojetos.Game.List.dto.OrdenacaoDTO;
import com.meusprojetos.Game.List.entities.Ordenacao;
import com.meusprojetos.Game.List.repositories.OrdenacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdenacaoService {

    @Autowired
    private OrdenacaoRepository ordenacaoRepository;

    @Transactional(readOnly = true)
    public List<OrdenacaoDTO> findAll() {
        List<Ordenacao> result = ordenacaoRepository.findAll();
        return result.stream().map(x -> new OrdenacaoDTO(x)).toList();
    }
}
