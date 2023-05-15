package com.cesar.elotech.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DataNascimentoValidador implements ConstraintValidator<DataNascimento, LocalDate> {

    public void initialize(DataNascimento dataNascimento) {
    }

    public boolean isValid(LocalDate dataNascimento, ConstraintValidatorContext context) {
        return dataNascimento == null || !dataNascimento.isAfter(LocalDate.now());
    }
}