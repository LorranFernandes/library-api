package io.github.LorranFernandes.libraryapi.validador;

import io.github.LorranFernandes.libraryapi.exceptions.CampoInvalidoException;
import io.github.LorranFernandes.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.LorranFernandes.libraryapi.model.Livro;
import io.github.LorranFernandes.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro) {
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }
        
        if(precoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatório");
        }
    }

    private boolean precoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    //Existe livro em duas condicoes
    //1 se estiver cadastrando pela primeira vez e o isbn existe no banco
    //2 se o id do livro que estiver cadastrando for diferente que o id do livro no banco
    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado.
                map(Livro::getId)
                .stream().
                anyMatch(id -> !id.equals(livro.getId()));
    }
}
