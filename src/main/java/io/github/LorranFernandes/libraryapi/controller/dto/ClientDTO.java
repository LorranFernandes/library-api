package io.github.LorranFernandes.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Client")
public record ClientDTO(
        @NotBlank(message = "campo obrigatório")
        String clientId,
        @NotBlank(message = "campo obrigatório")
        String clientSecret,
        @NotBlank(message = "campo obrigatório")
        String redirectUri,
        @NotBlank(message = "campo obrigatório")
        String scope){

}
