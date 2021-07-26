package br.com.magalu.wishlist.exceptions;

public class MaximumLimitException extends RuntimeException {
    public MaximumLimitException(final String errorMessage) {
        super(errorMessage);
    }
}
