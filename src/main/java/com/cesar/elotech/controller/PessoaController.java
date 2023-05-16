package com.cesar.elotech.controller;

import com.cesar.elotech.domain.Contato;
import com.cesar.elotech.domain.Pessoa;
import com.cesar.elotech.service.PessoaService;
import com.cesar.elotech.service.exception.DatabaseException;
import com.cesar.elotech.service.exception.ResourceNotFoundException;
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
@RequestMapping(value = "/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    public ResponseEntity<Page<Pessoa>> findAll(Pageable pageable) {
        Page<Pessoa> pages = service.findAll(pageable);
        return ResponseEntity.ok().body(pages);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
        Pessoa obj = service.findById(id);
        if (obj == null) {
            throw new ResourceNotFoundException(id);
        }
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Pessoa> insert(@RequestBody @Valid Pessoa obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody @Valid Pessoa obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

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

    @GetMapping(value = "/{id}/contatos")
    public ResponseEntity<List<Contato>> findContatos(@PathVariable Long id) {
        Pessoa obj = service.findById(id);
        return ResponseEntity.ok().body(obj.getContatos());
    }
}