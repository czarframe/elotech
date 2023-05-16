package com.cesar.elotech.controller;

import com.cesar.elotech.domain.Contato;
import com.cesar.elotech.service.ContatoService;
import com.cesar.elotech.service.exception.DatabaseException;
import com.cesar.elotech.service.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/contatos", produces = {"application/json"})
@Tag(name = "Contato")
public class ContatoController {

    @Autowired
    private ContatoService service;

    @Operation(summary = "Lista todos os Contatos", method = "GET")
    @GetMapping
    public ResponseEntity<Page<Contato>> findAll(Pageable pageable) {
        Page<Contato> pages = service.findAll(pageable);
        return ResponseEntity.ok().body(pages);
    }

    @ApiResponse(responseCode = "200", description = "Contato listado com sucesso!")
    @Operation(summary = "Lista um Contato por Id", method = "GET")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Contato> findById(@PathVariable Long id) {
        Contato obj = service.findById(id);
        if (obj == null) {
            throw new ResourceNotFoundException(id);
        }
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Salva um Contato", method = "POST")
    @ApiResponse(responseCode = "201", description = "Contato criado com sucesso!")
    @PostMapping
    public ResponseEntity<Contato> insert(@RequestBody @Valid Contato obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso!")
    @Operation(summary = "Atualiza um Contato por Id", method = "PUT")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Contato> update(@PathVariable Long id, @RequestBody @Valid Contato obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Deleta um Contato por Id", method = "DELETE")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            Contato obj = service.findById(id);
            if (obj == null) {
                throw new ResourceNotFoundException(id);
            }
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DatabaseException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}