package com.costrella.cechini.service.exceptions;

public class WorkerWrongPassException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WorkerWrongPassException() {
        super("WorkerWrongPassException");
    }

}
