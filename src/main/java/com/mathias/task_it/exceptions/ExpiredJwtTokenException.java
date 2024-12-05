package com.mathias.task_it.exceptions;

public class ExpiredJwtTokenException extends RuntimeException {
    public ExpiredJwtTokenException(String message) {
        super(message);
    }
}
