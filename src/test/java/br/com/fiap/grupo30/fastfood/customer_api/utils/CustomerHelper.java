package br.com.fiap.grupo30.fastfood.customer_api.utils;

import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.customer_api.domain.valueobjects.CPF;

public class CustomerHelper {

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final String DEFAULT_NAME = "John";
    private static final String DEFAULT_CPF = "33942748096";
    private static final String DEFAULT_EMAIL = "john@doe.com";

    /**
     * Creates a default Customer instance.
     */
    public static Customer createDefaultCustomer() {
        CPF cpf = new CPF(DEFAULT_CPF);
        CPF.calculateDigit(cpf.value(), 0);
        return new Customer(DEFAULT_CUSTOMER_ID, DEFAULT_NAME, cpf, DEFAULT_EMAIL);
    }

    /**
     * Creates a default Customer instance.
     */
    public static Customer createDefaultCustomerWithId() {
        return new Customer(DEFAULT_CUSTOMER_ID, DEFAULT_NAME, new CPF(DEFAULT_CPF), DEFAULT_EMAIL);
    }

    /**
     * Creates a Customer with specified ID and name.
     */
    public static Customer createCustomer(Long id, String name) {
        return new Customer(id, name, new CPF(DEFAULT_CPF), DEFAULT_EMAIL);
    }
}
