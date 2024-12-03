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
        customer = new Customer(1L, "John Doe", new CPF("81704243050"), "johndoe@example.com");
    }

    @Test
    void test_shouldCreateCustomer() {
        Customer createdCustomer =
                Customer.create("Jane Doe", "81704243050", "janedoe@example.com");

        assertNotNull(createdCustomer, "Customer can not be null");
        assertNull(createdCustomer.getId(), "Id can not be null");
        assertEquals("Jane Doe", createdCustomer.getName(), "Names must match");
        assertEquals("81704243050", createdCustomer.getCpf().value(), "CPF must match");
        assertEquals("janedoe@example.com", createdCustomer.getEmail(), "Email must match");
    }

    @Test
    void testToDTO() {
        CustomerDTO dto = customer.toDTO();

        assertNotNull(dto, "Customer can not be null");
        assertEquals(customer.getName(), dto.getName(), "Names must match");
        assertEquals(customer.getCpf().value(), dto.getCpf(), "CPF must match");
        assertEquals(customer.getEmail(), dto.getEmail(), "Email must match");
    }

    @Test
    void testToPersistence() {
        CustomerEntity entity = customer.toPersistence();

        assertNotNull(entity, "Customer can not be null");
        assertEquals(customer.getId(), entity.getId(), "Ids must match");
        assertEquals(customer.getName(), entity.getName(), "Names must match");
        assertEquals(customer.getCpf().value(), entity.getCpf(), "CPF must match");
        assertEquals(customer.getEmail(), entity.getEmail(), "Email must match");
    }

    @Test
    void testEquals_SameObject() {
        assertEquals(customer, customer, "Customers must match");
    }

    @Test
    void testEquals_DifferentObjectSameCpf() {
        Customer anotherCustomer =
                new Customer(2L, "John Smith", new CPF("81704243050"), "johnsmith@example.com");
        assertEquals(customer, anotherCustomer, "Customers must match");
    }

    @Test
    void testEquals_DifferentCpf() {
        Customer differentCustomer =
                new Customer(3L, "Jane Doe", new CPF("56107050027"), "janedoe@example.com");
        assertNotEquals(customer, differentCustomer, "Customers must match");
    }

    @Test
    void testHashCode_SameCpf() {
        Customer anotherCustomer =
                new Customer(2L, "John Smith", new CPF("81704243050"), "johnsmith@example.com");
        assertEquals(customer.hashCode(), anotherCustomer.hashCode(), "Customers must match");
    }

    @Test
    void testHashCode_DifferentCpf() {
        Customer differentCustomer =
                new Customer(3L, "Jane Doe", new CPF("56107050027"), "janedoe@example.com");
        assertNotEquals(customer.hashCode(), differentCustomer.hashCode(), "Customers must match");
    }
}
