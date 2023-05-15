package com.cesar.elotech.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataNascimentoValidador.class)
public @interface DataNascimento {
    String message() default "A data de nascimento deve ser anterior ou igual à data atual";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}