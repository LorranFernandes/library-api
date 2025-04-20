package io.github.LorranFernandes.libraryapi.repository;

import io.github.LorranFernandes.libraryapi.model.Autor;
import io.github.LorranFernandes.libraryapi.model.GeneroLivro;
import io.github.LorranFernandes.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;
    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Ronaldo");
        autor.setNacionalidade("brasileira");
        autor.setDataNascimento(LocalDate.of(1995, 6, 1));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("ed088c0d-5ea8-434d-acae-c3e168c332d6");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println(autorEncontrado);
            autorEncontrado.setNome("Ronaldo");

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println(repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("ed088c0d-5ea8-434d-acae-c3e168c332d6");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("4d071bf3-cc0e-48af-bba3-53c0674e00bd");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Ronildo");
        autor.setNacionalidade("americano");
        autor.setDataNascimento(LocalDate.of(1985, 2, 1));

        Livro livro = new Livro();
        livro.setIsbn("499459");
        livro.setTitulo("A morte de roma");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setDataPublicacao(LocalDate.of(2010, 6, 1));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("165564");
        livro2.setTitulo("A morte da italia");
        livro2.setPreco(BigDecimal.valueOf(100));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setDataPublicacao(LocalDate.of(2011, 6, 1));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosTest(){
        UUID id = UUID.fromString("3e53901d-9fa4-4342-87d8-990f384702e1");
        var autor= repository.findById(id).get();

        //Buscar os livros do autor
        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}
