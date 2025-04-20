package io.github.LorranFernandes.libraryapi.controller.mappers;

import io.github.LorranFernandes.libraryapi.controller.dto.UsuarioDTO;
import io.github.LorranFernandes.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
