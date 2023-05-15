package com.cesar.elotech.service.exception;

import java.io.Serial;

public class ConstraintViolationException extends Throwable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ConstraintViolationException(String msg) {
        super(msg);
    }
}
