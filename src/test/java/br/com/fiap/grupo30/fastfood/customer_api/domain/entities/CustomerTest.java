package br.com.fiap.grupo30.fastfood.customer_api.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import br.com.fiap.grupo30.fastfood.customer_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "John Doe 1", new CPF("77503989025"), "johndoe1@example.com");
    }

    @Test
    void testCreateCustomer() {
        Customer createdCustomer =
                Customer.create("Jane Doe 2", "70671547070", "janedoe@example.com");
        assertEquals("janedoe3@example.com", createdCustomer.getEmail(), "should have same value");
    }

    @Test
    void testToDTO() {
        CustomerDTO dto = customer.toDTO();
        assertEquals(customer.getEmail(), dto.getEmail(), "should have same value");
    }

    @Test
    void testToPersistence() {
        CustomerEntity entity = customer.toPersistence();
        assertEquals(customer.getEmail(), entity.getEmail(), "should have same value");
    }

    @Test
    void testEquals_SameObject() {
        assertEquals(customer, customer, "should have same value");
    }

    @Test
    void testEquals_DifferentObjectSameCpf() {
        Customer anotherCustomer =
                new Customer(2L, "John Smith", new CPF("64528738066"), "johnsmith@example.com");
        assertEquals(customer, anotherCustomer, "should have same value");
    }

    @Test
    void testEquals_DifferentCpf() {
        Customer differentCustomer =
                new Customer(3L, "Jane Doe 4", new CPF("21999393082"), "janedoe4@example.com");
        assertNotEquals(customer, differentCustomer, "should have same value");
    }

    @Test
    void testHashCode_SameCpf() {
        Customer anotherCustomer =
                new Customer(2L, "John Smith", new CPF("40421649003"), "johnsmith@example.com");
        assertEquals(customer.hashCode(), anotherCustomer.hashCode(), "should have same value");
    }

    @Test
    void testHashCode_DifferentCpf() {
        Customer differentCustomer =
                new Customer(3L, "Jane Doe 5", new CPF("00911206086"), "janedoe5@example.com");
        assertNotEquals(customer.hashCode(), differentCustomer.hashCode(), "should have same value");
    }
}
