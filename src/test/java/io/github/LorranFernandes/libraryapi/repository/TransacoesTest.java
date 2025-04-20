package io.github.LorranFernandes.libraryapi.repository;

import io.github.LorranFernandes.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    @Test
    void transacaoSimplesTest(){
        transacaoService.executar();
    }

    @Test
    void transacaoEstadoMenagerTest(){
        transacaoService.atualizacaoSemAtualizar();
    }
}
