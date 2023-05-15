package com.cesar.elotech.service;

import com.cesar.elotech.domain.Pessoa;
import com.cesar.elotech.repository.PessoaRepository;
import com.cesar.elotech.service.exception.DatabaseException;
import com.cesar.elotech.service.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Page<Pessoa> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Pessoa findById(Long id) {
        Optional<Pessoa> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Pessoa insert(Pessoa obj) {
        return repository.save(obj);
    }

    public Pessoa update(Long id, Pessoa obj) {
        try {
            Pessoa entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Pessoa entity, Pessoa obj) {
        entity.setNome(obj.getNome());
        entity.setCpf(obj.getCpf());
        entity.setDataNascimento(obj.getDataNascimento());
    }

    public void delete(Long id) {
        try {
            repository.findById(id);
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}