package br.com.fiap.grupo30.fastfood.customer_api.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.fiap.grupo30.fastfood.customer_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest {

    private Customer customer;
    private Customer otherCustomer;

    private static final CPF FIRST_CPF = new CPF("81704243050");
    private static final CPF SECOND_CPF = new CPF("56107050027");

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "John Doe", FIRST_CPF, "johndoe@example.com");
        otherCustomer = new Customer(2L, "Jane Doe", SECOND_CPF, "janedoe@example.com");
    }

    @Test
    void test_shouldCreateCustomer() {
        // assertNotNull(customer, "Customer can not be null");
        // assertNotNull(customer.getId(), "Id can not be null");
        // assertNotNull(customer.getName(), "Names can not be null");
        // assertNotNull(customer.getCpf().value(), "CPF can not be null");
        assertNotNull(customer.getEmail(), "Email can not be null");
    }

    @Test
    void testToDTO() {
        CustomerDTO dto = customer.toDTO();
        assertEquals(customer.getEmail(), dto.getEmail(), "should have same email");
    }

    @Test
    void testToPersistence() {
        CustomerEntity entity = customer.toPersistence();
        entity.prePersist();
        entity.preUpdate();
        entity.preRemove();

        assertEquals(customer.getEmail(), entity.getEmail(), "should have same value");
    }

    @Test
    void testEquals_SameObject() {
        assertEquals(customer, customer, "Customers must match when exactly equal");
    }

    @Test
    void testEquals_DifferentObjectSameCpf() {
        Customer sameCustomer = new Customer(1L, "John Smith", FIRST_CPF, "johnsmith@example.com");
        assertEquals(customer, sameCustomer, "Customers must match when same cpf");
    }

    @Test
    void testEquals_DifferentCpf() {
        assertNotEquals(customer, otherCustomer, "Customers must not match when differet cpf");
    }

    @Test
    void testHashCode_SameCpf() {
        Customer sameCustomer = new Customer(1L, "John Smith", FIRST_CPF, "johnsmith@example.com");
        assertEquals(
                customer.hashCode(),
                sameCustomer.hashCode(),
                "Customers hashcode must match when same cpf");
    }

    @Test
    void testHashCode_DifferentCpf() {
        assertNotEquals(
                customer.hashCode(),
                otherCustomer.hashCode(),
                "Customers hashcode must not match when different cpf");
    }
}
