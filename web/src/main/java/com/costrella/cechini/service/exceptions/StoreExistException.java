package com.costrella.cechini.service.exceptions;

public class StoreExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StoreExistException() {
        super("Już istnieje sklep o takiej nazwie i adresie!");
    }

}
