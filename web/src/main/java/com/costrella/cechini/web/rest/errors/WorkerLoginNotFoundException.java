package com.costrella.cechini.web.rest.errors;

public class WorkerLoginNotFoundException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public WorkerLoginNotFoundException() {
        super(ErrorConstants.DEFAULT_TYPE, "Nie ma takiego użytkownika w systemie Cechini!", "userManagement", "wrongLogin");
    }
}
