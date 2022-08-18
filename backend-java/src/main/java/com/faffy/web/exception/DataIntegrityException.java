package com.faffy.web.exception;

public class DataIntegrityException extends Exception{
    public DataIntegrityException() {
        super();
    }

    public DataIntegrityException(String message) {
        super(message);
    }
}
