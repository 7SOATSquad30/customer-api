package br.com.fiap.grupo30.fastfood.customer_api.utils;

import org.springframework.validation.FieldError;

public class FieldErrorHelper {

    /**
     * Creates a default FieldError instance.
     */
    public static FieldError createDefaultFieldError() {
        return new FieldError("customer", "name", "Name is required");
    }
}
