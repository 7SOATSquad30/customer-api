package br.com.fiap.grupo30.fastfood.customer_api.utils;


import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Customer;

public class CustomerHelper {

    private static final String DEFAULT_CUSTOMER_ID = 1;
    private static final String DEFAULT_NAME = "John";
    private static final String DEFAULT_CPF = "33942748096";
    private static final String DEFAULT_EMAIL = "john@doe.com";

    /**
     * Creates a default Customer instance.
     */
    public static Customer createDefaultCustomer() {
        return new Customer(DEFAULT_NAME, DEFAULT_CPF, DEFAULT_EMAIL);
    }

    /**
     * Creates a default Customer instance.
     */
    public static Customer createDefaultCustomerWithId() {
        return new Customer(DEFAULT_CUSTOMER_ID, DEFAULT_NAME, DEFAULT_CPF, DEFAULT_EMAIL);
    }

    /**
     * Creates a Customer with specified ID and name.
     */
    public static Customer createCustomer(Long id, String name) {
        return new Customer(id, name);
    }

}
