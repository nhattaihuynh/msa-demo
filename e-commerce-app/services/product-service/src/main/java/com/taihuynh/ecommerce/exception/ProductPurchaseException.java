package com.taihuynh.ecommerce.exception;

public class ProductPurchaseException extends RuntimeException {
    public ProductPurchaseException(String message) {
        super(message);
    }

    public ProductPurchaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
