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
        customer = new Customer(1L, "John Doe", new CPF("77503989025"), "johndoe@example.com");
    }

    @Test
    void testCreateCustomer() {
        Customer createdCustomer =
                Customer.create("Jane Doe", "77503989025", "janedoe@example.com");

        assertNotNull(createdCustomer);
        assertNull(createdCustomer.getId());
        assertEquals("Jane Doe", createdCustomer.getName());
        assertEquals("77503989025", createdCustomer.getCpf().value());
        assertEquals("janedoe@example.com", createdCustomer.getEmail());
    }

    @Test
    void testToDTO() {
        CustomerDTO dto = customer.toDTO();

        assertNotNull(dto);
        assertEquals(customer.getName(), dto.getName());
        assertEquals(customer.getCpf().value(), dto.getCpf());
        assertEquals(customer.getEmail(), dto.getEmail());
    }

    @Test
    void testToPersistence() {
        CustomerEntity entity = customer.toPersistence();

        assertNotNull(entity);
        assertEquals(customer.getId(), entity.getId());
        assertEquals(customer.getName(), entity.getName());
        assertEquals(customer.getCpf().value(), entity.getCpf());
        assertEquals(customer.getEmail(), entity.getEmail());
    }

    @Test
    void testEquals_SameObject() {
        assertEquals(customer, customer);
    }

    @Test
    void testEquals_DifferentObjectSameCpf() {
        Customer anotherCustomer =
                new Customer(2L, "John Smith", new CPF("77503989025"), "johnsmith@example.com");
        assertEquals(customer, anotherCustomer);
    }

    @Test
    void testEquals_DifferentCpf() {
        Customer differentCustomer =
                new Customer(3L, "Jane Doe", new CPF("21999393082"), "janedoe@example.com");
        assertNotEquals(customer, differentCustomer);
    }

    @Test
    void testHashCode_SameCpf() {
        Customer anotherCustomer =
                new Customer(2L, "John Smith", new CPF("77503989025"), "johnsmith@example.com");
        assertEquals(customer.hashCode(), anotherCustomer.hashCode());
    }

    @Test
    void testHashCode_DifferentCpf() {
        Customer differentCustomer =
                new Customer(3L, "Jane Doe", new CPF("21999393082"), "janedoe@example.com");
        assertNotEquals(customer.hashCode(), differentCustomer.hashCode());
    }
}
