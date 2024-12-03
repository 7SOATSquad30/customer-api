package br.com.fiap.grupo30.fastfood.customer_api.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.grupo30.fastfood.customer_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;

class CustomerTest {

    private Customer customer;

    private static final CPF FIRST_CPF = new CPF("81704243050");
    private static final CPF SECOND_CPF = new CPF("56107050027");

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "John Doe", FIRST_CPF, "johndoe@example.com");
    }

    @Test
    void test_shouldCreateCustomer() {
        Customer createdCustomer =
                Customer.create("Jane Doe", FIRST_CPF.value(), "janedoe@example.com");

        assertNotNull(createdCustomer, "Customer can not be null");
        assertNull(createdCustomer.getId(), "Id can not be null");
        assertEquals("Jane Doe", createdCustomer.getName(), "Names must match");
        assertEquals(FIRST_CPF.value(), createdCustomer.getCpf().value(), "CPF must match");
        assertEquals("janedoe@example.com", createdCustomer.getEmail(), "Email must match");
    }

    @Test
    void testToDTO() {
        CustomerDTO dto = customer.toDTO();
        assertEquals(customer.getEmail(), dto.getEmail());
    }

    @Test
    void testToPersistence() {
        CustomerEntity entity = customer.toPersistence();
        assertEquals(customer.getEmail(), entity.getEmail(), "should have same value");
    }

    @Test
    void testEquals_SameObject() {
        assertEquals(customer, customer, "Customers must match");
    }

    @Test
    void testEquals_DifferentObjectSameCpf() {
        Customer anotherCustomer =
                new Customer(2L, "John Smith", FIRST_CPF, "johnsmith@example.com");
        assertEquals(customer, anotherCustomer, "Customers must match");
    }

    @Test
    void testEquals_DifferentCpf() {
        Customer differentCustomer =
                new Customer(3L, "Jane Doe", SECOND_CPF, "janedoe@example.com");
        assertNotEquals(customer, differentCustomer, "Customers must match");
    }

    @Test
    void testHashCode_SameCpf() {
        Customer anotherCustomer =
                new Customer(2L, "John Smith", FIRST_CPF, "johnsmith@example.com");
        assertEquals(customer.hashCode(), anotherCustomer.hashCode(), "Customers must match");
    }

    @Test
    void testHashCode_DifferentCpf() {
        Customer differentCustomer =
                new Customer(3L, "Jane Doe", SECOND_CPF, "janedoe@example.com");
        assertNotEquals(customer.hashCode(), differentCustomer.hashCode(), "Customers must match");
    }
}
