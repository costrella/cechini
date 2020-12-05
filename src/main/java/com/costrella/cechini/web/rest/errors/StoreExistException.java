package com.costrella.cechini.web.rest.errors;

public class StoreExistException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public StoreExistException() {
        super(ErrorConstants.CONSTRAINT_VIOLATION_TYPE, "Już istnieje sklep o takiej nazwie i adresie!", "store", "storeexists");
    }
}
