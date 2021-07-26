package br.com.magalu.wishlist.exceptions;


public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(final String errorMessage) {
        super(errorMessage);
    }
}