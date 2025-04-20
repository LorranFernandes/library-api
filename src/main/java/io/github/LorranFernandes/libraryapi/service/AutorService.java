package io.github.LorranFernandes.libraryapi.service;

import io.github.LorranFernandes.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.LorranFernandes.libraryapi.model.Autor;
import io.github.LorranFernandes.libraryapi.model.Usuario;
import io.github.LorranFernandes.libraryapi.repository.AutorRepository;
import io.github.LorranFernandes.libraryapi.repository.LivroRepository;
import io.github.LorranFernandes.libraryapi.security.SecurityService;
import io.github.LorranFernandes.libraryapi.validador.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessario que o autor exista na base de dados");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivros(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido excluir autor que possui Livros cadastrados");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if(nome != null){
            return repository.findByNome(nome);
        }
        if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }
        return repository.findAll();
    }

    public List<Autor> pesquisaByExemple(String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher.matching().
                withIgnoreNullValues().
                withIgnoreCase().
                withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);

        return repository.findAll(autorExample);
    }

    public boolean possuiLivros(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
