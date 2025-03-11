package com.omega.cashflow.exception;

public class MovimentacaoException extends RuntimeException {
    public MovimentacaoException(String message) {
        super(message);
    }

    public MovimentacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
