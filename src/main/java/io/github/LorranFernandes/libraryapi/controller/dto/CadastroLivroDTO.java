package io.github.LorranFernandes.libraryapi.controller.dto;

import io.github.LorranFernandes.libraryapi.model.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Livro")
public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "campo obrigatório")
        String isbn,
        @NotBlank(message = "campo obrigatório")
        String titulo,
        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser uma data futura")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull(message = "campo obrigatório")
        UUID idAutor) {
}
