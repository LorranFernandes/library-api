package io.github.LorranFernandes.libraryapi.controller.mappers;

import io.github.LorranFernandes.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.LorranFernandes.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.LorranFernandes.libraryapi.model.Livro;
import io.github.LorranFernandes.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AutorMapper.class})
public abstract class LivroMapper { // foi usado uma abstract class ao inves de interface para conseguir pegar um livro pelo id na base

    @Autowired
    AutorRepository autorRepository;

    // A expression foi usada para ligar o id autor ao ator
    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
