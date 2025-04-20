package io.github.LorranFernandes.libraryapi.service;

import io.github.LorranFernandes.libraryapi.model.GeneroLivro;
import io.github.LorranFernandes.libraryapi.model.Livro;
import io.github.LorranFernandes.libraryapi.model.Usuario;
import io.github.LorranFernandes.libraryapi.repository.LivroRepository;
import io.github.LorranFernandes.libraryapi.security.SecurityService;
import io.github.LorranFernandes.libraryapi.validador.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static io.github.LorranFernandes.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;
    private final SecurityService securityService;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }

    public Page<Livro> pesquisar(
            String isbn,
            String nomeAutor,
            String titulo,
            GeneroLivro genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina) {

        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (isbn != null) {
            specs = specs.and(isbnEquals(isbn));
        }

        if (titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }

        if (nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        if (genero != null) {
            specs = specs.and(generoEquals(genero));
        }

        if (anoPublicacao != null) {
            specs = specs.and(anoPublicacaoEquals(anoPublicacao));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs,pageRequest);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessario que o livro exista na base de dados");
        }
        validator.validar(livro);
        repository.save(livro);
    }
}
