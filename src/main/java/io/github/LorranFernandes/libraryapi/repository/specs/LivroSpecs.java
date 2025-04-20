package io.github.LorranFernandes.libraryapi.repository.specs;

import io.github.LorranFernandes.libraryapi.model.GeneroLivro;
import io.github.LorranFernandes.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEquals(String isbn) {
        return ((root, query, cb) ->
                cb.equal(root.get("isbn"),isbn));
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")),"%"+titulo.toUpperCase()+"%");
    }

    public static Specification<Livro> generoEquals(GeneroLivro genero) {
        return (root, query, cb) ->
                cb.equal(root.get("genero"),genero);
    }

    public static Specification<Livro> anoPublicacaoEquals(Integer anoPublicacao) {
        return (root, query, cb) ->
                cb.equal( cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")),anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nomeAutor) {
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.INNER);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nomeAutor.toUpperCase() + "%");

//            return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nomeAutor.toUpperCase() + "%");
        };
    }



}
