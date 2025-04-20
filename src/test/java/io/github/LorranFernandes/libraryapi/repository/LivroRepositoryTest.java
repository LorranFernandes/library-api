package io.github.LorranFernandes.libraryapi.repository;

import io.github.LorranFernandes.libraryapi.model.Autor;
import io.github.LorranFernandes.libraryapi.model.GeneroLivro;
import io.github.LorranFernandes.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("123");
        livro.setTitulo("Livro Test");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setDataPublicacao(LocalDate.of(2020, 1, 1));

        Autor autor = autorRepository
                .findById(UUID.fromString("4d071bf3-cc0e-48af-bba3-53c0674e00bd")).
                orElse(null);

        livro.setAutor(autor);
        repository.save(livro);
    }

    @Test
    void salvarAutorLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("123");
        livro.setTitulo("Livro Test2");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setDataPublicacao(LocalDate.of(2020, 1, 1));

        Autor autor = new Autor();
        autor.setNome("Remura");
        autor.setNacionalidade("brasileira");
        autor.setDataNascimento(LocalDate.of(1985, 6, 1));

        autorRepository.save(autor);
        livro.setAutor(autor);
        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("123");
        livro.setTitulo("Livro Test2");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setDataPublicacao(LocalDate.of(2020, 1, 1));

        Autor autor = new Autor();
        autor.setNome("Remura");
        autor.setNacionalidade("brasileira");
        autor.setDataNascimento(LocalDate.of(1985, 6, 1));

        livro.setAutor(autor);
        repository.save(livro);
    }

    @Test
    void atualizarAutorLivroTest(){
        UUID id = UUID.fromString("9c09be13-d4d8-40f0-9ab1-105d8a3dc5d5");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("03bc75ef-ab88-45ee-81ea-ff063e2de75d");
        Autor remura = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(remura);
        repository.save(livroParaAtualizar);
    }

    @Test
    void deletarTest(){
        UUID id = UUID.fromString("e46385e2-4713-4343-a3f4-3a424cfc9b41");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("9c09be13-d4d8-40f0-9ab1-105d8a3dc5d5");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> lista = repository.findByTitulo("A morte de roma");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest(){
        Optional<Livro> livro = repository.findByIsbn("499459");
        livro.ifPresent(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest(){
        var preco = BigDecimal.valueOf(101.00);
        var titulo = "A morte de roma";

        List<Livro> lista = repository.findByTituloAndPreco(titulo, preco);
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQLTest(){
        List<Livro> resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosTest(){
        List<Autor> resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void titulosNaoRepetidoTest(){
        List<String> resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileirosTest(){
        List<String> resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroNamedQueryParamTest(){
        var resultado = repository.findByGeneroNamedParameters(GeneroLivro.BIOGRAFIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalQueryParamTest(){
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.BIOGRAFIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest(){
        repository.deleteByGenero(GeneroLivro.ROMANCE);
    }

    @Test
    void updateDataPublicacaoPorTituloTest(){
        repository.updateDataPublicacaoByTitulo(LocalDate.of(2021,6,20),"A morte de roma");
    }
}
