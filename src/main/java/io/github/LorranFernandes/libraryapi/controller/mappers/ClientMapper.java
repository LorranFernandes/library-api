package io.github.LorranFernandes.libraryapi.controller.mappers;

import io.github.LorranFernandes.libraryapi.controller.dto.ClientDTO;
import io.github.LorranFernandes.libraryapi.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO dto);
}
