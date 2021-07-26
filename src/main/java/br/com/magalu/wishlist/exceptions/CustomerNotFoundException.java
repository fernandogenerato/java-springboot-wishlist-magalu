package br.com.magalu.wishlist.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(final String errorMessage) {
        super(errorMessage);
    }
}

