package com.cesar.elotech.controller;

import com.cesar.elotech.domain.Contato;
import com.cesar.elotech.domain.Pessoa;
import com.cesar.elotech.service.PessoaService;
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
import java.util.List;

@RestController
@RequestMapping(value = "/pessoas", produces = {"application/json"})
@Tag(name = "Pessoa")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @Operation(summary = "Lista todas as Pessoas", method = "GET")
    @GetMapping
    public ResponseEntity<Page<Pessoa>> findAll(Pageable pageable) {
        Page<Pessoa> pages = service.findAll(pageable);
        return ResponseEntity.ok().body(pages);
    }

    @ApiResponse(responseCode = "200", description = "Pessoa listada com sucesso!")
    @Operation(summary = "Lista uma Pessoa por Id", method = "GET")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
        Pessoa obj = service.findById(id);
        if (obj == null) {
            throw new ResourceNotFoundException(id);
        }
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Salva uma Pessoa", method = "POST")
    @ApiResponse(responseCode = "201", description = "Pessoa cadastrada com sucesso!")
    @PostMapping
    public ResponseEntity<Pessoa> insert(@RequestBody @Valid Pessoa obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @Operation(summary = "Atualiza uma Pessoa por Id", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso!")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody @Valid Pessoa obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Deleta uma Pessoa por Id", method = "DELETE")
    @ApiResponse(responseCode = "204", description = "Pessoa excluída com sucesso!")
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            Pessoa obj = service.findById(id);
            if (obj == null) {
                throw new ResourceNotFoundException(id);
            }
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DatabaseException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Operation(summary = "Lista todos os Contatos de uma Pessoa por Id", method = "GET")
    @ApiResponse(responseCode = "200", description = "Listado com sucesso!")
    @GetMapping(value = "/{id}/contatos")
    public ResponseEntity<List<Contato>> findContatos(@PathVariable Long id) {
        Pessoa obj = service.findById(id);
        return ResponseEntity.ok().body(obj.getContatos());
    }
}