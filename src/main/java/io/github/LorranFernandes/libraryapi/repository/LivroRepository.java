package io.github.LorranFernandes.libraryapi.repository;

import io.github.LorranFernandes.libraryapi.model.Autor;
import io.github.LorranFernandes.libraryapi.model.GeneroLivro;
import io.github.LorranFernandes.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * @see LivroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    // Query method
    // select * from livro where id_autor = ?
    List<Livro> findByAutor(Autor autor);

    //select * from livro where titulo = ?
    List<Livro> findByTitulo(String titulo);

    //select * from livro where isbn = ?
    Optional<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = ? and isbn = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    // select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // JPQL -> referencia as entidades e as propriedades
    // select l.* from livro as l order by l.titulo, l.preco
    @Query(" select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    // select a.* from livro l join autor a on a.id = l.id_autor
    @Query(" select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    // select distinct l.* from livro l
    @Query(" select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    // select distinct l.genero from livro l join autor a on a.id = l.id_autor
    // where a.nacionalidade = 'brasileira' order by l.genero
    @Query("""
    select distinct l.genero from Livro l join l.autor a where a.nacionalidade = 'brasileira' order by l.genero
""")
    List<String> listarGenerosAutoresBrasileiros();

    // named parameters -> parametros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGeneroNamedParameters( @Param("genero") GeneroLivro generoLivro, @Param("paramOrdenacao") String nomePropriedade);

    // positional parameters -> parametros posiciona
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters( GeneroLivro generoLivro, String nomePropriedade);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query(" update Livro set dataPublicacao = ?1 where titulo = ?2 ")
    void updateDataPublicacaoByTitulo(LocalDate dataPublicacao, String titulo);

    boolean existsByAutor(Autor autor);
}
