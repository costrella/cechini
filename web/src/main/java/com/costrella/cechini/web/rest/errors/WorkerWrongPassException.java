package com.costrella.cechini.web.rest.errors;

public class WorkerWrongPassException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public WorkerWrongPassException() {
        super(ErrorConstants.INVALID_PASSWORD_TYPE, "Niepoprawne hasło", "userManagement", "wrongPass");
    }
}
