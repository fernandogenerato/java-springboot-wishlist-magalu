package br.com.magalu.wishlist.gateways.rest;

import br.com.magalu.wishlist.exceptions.CustomerNotFoundException;
import br.com.magalu.wishlist.exceptions.MaximumLimitException;
import br.com.magalu.wishlist.exceptions.ProductNotFoundException;
import br.com.magalu.wishlist.gateways.json.ApiErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class WishlistControllerErrorHandler {

    private static final String INTERNAL_ERROR_MESSAGE = "Unexpected error.";
    private static final String LIMIT_ERROR_MESSAGE = "Maximum Limit of 20 Products.";
    private static final String CUSTOMER_NOT_FOUND = "Customer not found, check customer ID.";
    private static final String PRODUCT_NOT_FOUND = "Product not found, check product ID.";

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiErrorResponse customerNotFoundException(final RuntimeException exception) {
        log.error(exception.getLocalizedMessage());
        return ApiErrorResponse.builder().message(CUSTOMER_NOT_FOUND).build();
    }

    @ExceptionHandler(value = {ProductNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiErrorResponse productNotFoundException(final RuntimeException exception) {
        log.error(exception.getLocalizedMessage());
        return ApiErrorResponse.builder().message(PRODUCT_NOT_FOUND).build();
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ApiErrorResponse notExpectedException(final Exception exception) {
        log.error(exception.getLocalizedMessage());
        return ApiErrorResponse.builder().message(INTERNAL_ERROR_MESSAGE).build();
    }

    @ExceptionHandler(value = {MaximumLimitException.class})
    @ResponseStatus(HttpStatus.OK)
    protected ApiErrorResponse maximuxLimiteException(final RuntimeException exception) {
        log.error(exception.getLocalizedMessage());
        return ApiErrorResponse.builder().message(LIMIT_ERROR_MESSAGE).build();
    }


}

