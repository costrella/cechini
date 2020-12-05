package com.costrella.cechini.service;

public class StoreExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StoreExistException() {
        super("Już istnieje sklep o takiej nazwie i adresie!");
    }

}
