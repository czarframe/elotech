package com.cesar.elotech.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated

@Entity
public class Contato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(min = 3, max = 100)
    private String nome;

    @NotNull
    @Length(min = 10, max = 12)
    private String telefone;

    @NotNull
    @Email
    private String email;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato = (Contato) o;
        return Objects.equals(id, contato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}