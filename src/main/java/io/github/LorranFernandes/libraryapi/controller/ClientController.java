package io.github.LorranFernandes.libraryapi.controller;

import io.github.LorranFernandes.libraryapi.controller.dto.ClientDTO;
import io.github.LorranFernandes.libraryapi.controller.mappers.ClientMapper;
import io.github.LorranFernandes.libraryapi.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody @Valid ClientDTO dto){
        log.info("Registrando novo Client: {} com scope {} ", dto.clientId(), dto.scope());

        var client = mapper.toEntity(dto);
        service.salvar(client);
    }

}
