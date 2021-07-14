package com.store.apicontents.exceptions;

import org.springframework.http.HttpStatus;

public class CryptoStoreException extends RuntimeException{

    private HttpStatus status;

    public CryptoStoreException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
