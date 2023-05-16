package com.cesar.elotech.repository;

import com.cesar.elotech.domain.PessoaContato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaContatoRepository extends JpaRepository<PessoaContato, Long> {
    PessoaContato findByContatoId(Long contatoId);
}
