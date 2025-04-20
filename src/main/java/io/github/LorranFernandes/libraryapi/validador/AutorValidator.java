package io.github.LorranFernandes.libraryapi.validador;

import io.github.LorranFernandes.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.LorranFernandes.libraryapi.model.Autor;
import io.github.LorranFernandes.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        if(existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if(autor.getId() == null){ // Verifica se é um autor existente no banco
            return autorEncontrado.isPresent();
        }

        // Verifica se o autor que será alterado no banco não é igual a um autor existente
        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
