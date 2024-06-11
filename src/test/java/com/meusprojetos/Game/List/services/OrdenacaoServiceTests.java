package com.meusprojetos.Game.List.services;

import com.meusprojetos.Game.List.dto.OrdenacaoDTO;
import com.meusprojetos.Game.List.entities.Ordenacao;
import com.meusprojetos.Game.List.repositories.OrdenacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class OrdenacaoServiceTests {

    @InjectMocks
    private OrdenacaoService ordenacaoService;

    @Mock
    private OrdenacaoRepository ordenacaoRepository;

    private Ordenacao ordenacao;
    private OrdenacaoDTO ordenacaoDTO;

    @BeforeEach
    void setUp() throws Exception {

        ordenacao = new Ordenacao();
        ordenacaoDTO = new OrdenacaoDTO(ordenacao);

        Mockito.when(ordenacaoRepository.findAll()).thenReturn(List.of(ordenacao));
    }

    @Test
    public void findAllShouldReturnListOrdenacaoDTO() {

        List<OrdenacaoDTO> result = ordenacaoService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.iterator().next().getPosicao(), ordenacaoDTO.getPosicao());
    }
}
