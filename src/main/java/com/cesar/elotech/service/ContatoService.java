package com.cesar.elotech.service;

import com.cesar.elotech.domain.Contato;
import com.cesar.elotech.domain.Pessoa;
import com.cesar.elotech.domain.PessoaContato;
import com.cesar.elotech.repository.ContatoRepository;
import com.cesar.elotech.repository.PessoaContatoRepository;
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
public class ContatoService {

    @Autowired
    private ContatoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaContatoRepository pessoaContatoRepository;

    public Page<Contato> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Contato findById(Long id) {
        Optional<Contato> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Contato insert(Contato obj) {
        Contato contatoSalvo = repository.save(obj);
        Pessoa pessoa = obj.getPessoa();
        if (pessoa != null && pessoaRepository.findById(pessoa.getId()).isPresent()) {
            PessoaContato pessoaContato = new PessoaContato();
            pessoaContato.setPessoa(pessoa);
            pessoaContato.setContato(obj);
            pessoaContatoRepository.save(pessoaContato);
        }
        return contatoSalvo;
    }

    public Contato update(Long id, Contato obj) {
        try {
            Contato entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Contato entity, Contato obj) {
        entity.setNome(obj.getNome());
        entity.setTelefone(obj.getTelefone());
        entity.setEmail(obj.getEmail());
    }

    public void delete(Long id) {
        try {
            if (pessoaContatoRepository.findByContatoId(id) != null) {
                pessoaContatoRepository.deleteById(id);
            }
            repository.findById(id);
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}