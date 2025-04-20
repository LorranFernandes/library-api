package io.github.LorranFernandes.libraryapi.service;

import io.github.LorranFernandes.libraryapi.model.Autor;
import io.github.LorranFernandes.libraryapi.model.GeneroLivro;
import io.github.LorranFernandes.libraryapi.model.Livro;
import io.github.LorranFernandes.libraryapi.repository.AutorRepository;
import io.github.LorranFernandes.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("9c09be13-d4d8-40f0-9ab1-105d8a3dc5d5")).orElse(null);
        livro.setTitulo("Novo titulo");
    }

    @Transactional
    public void executar(){

        // salva o autor
        Autor autor = new Autor();
        autor.setNome("Francisca Test");
        autor.setNacionalidade("brasileira");
        autor.setDataNascimento(LocalDate.of(1985, 6, 1));

        autorRepository.save(autor);

        //salvar o livro
        Livro livro = new Livro();
        livro.setIsbn("14654");
        livro.setTitulo("Livro da francisca");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setDataPublicacao(LocalDate.of(2020, 1, 1));

        livro.setAutor(autor);
        livroRepository.save(livro);

        if (autor.getNome().equals("Francisca Test")) {
            throw new RuntimeException("Rollback!!");
        }
    }
}
