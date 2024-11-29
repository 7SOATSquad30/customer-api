package br.com.fiap.grupo30.fastfood.customer_api.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;
import org.junit.jupiter.api.Test;

class CustomerTest {

    private static final String CUSTOMER_NAME = "John";

    @Test
    void shouldCreateCustomerWithNullId() {
        // Act
        Customer customer = Customer.create(CUSTOMER_NAME);

        // Assert
        assertThat(customer.getId()).isNull();
    }

    @Test
    void shouldCreateCustomerWithCorrectName() {
        // Act
        Customer customer = Customer.create(CUSTOMER_NAME);

        // Assert
        assertThat(customer.getName()).isEqualTo(CUSTOMER_NAME);
    }

    @Test
    void shouldConvertToDTOWithCorrectName() {
        // Arrange
        Customer customer = new Customer(1L, CUSTOMER_NAME);

        // Act
        CustomerDTO dto = customer.toDTO();

        // Assert
        assertThat(dto.getName()).isEqualTo(customer.getName());
    }

    @Test
    void shouldConvertToPersistenceEntityWithCorrectId() {
        // Arrange
        Customer customer = new Customer(1L, CUSTOMER_NAME);

        // Act
        CustomerEntity entity = customer.toPersistence();

        // Assert
        assertThat(entity.getId()).isEqualTo(customer.getId());
    }

    @Test
    void shouldConvertToPersistenceEntityWithCorrectName() {
        // Arrange
        Customer customer = new Customer(1L, CUSTOMER_NAME);

        // Act
        CustomerEntity entity = customer.toPersistence();

        // Assert
        assertThat(entity.getName()).isEqualTo(customer.getName());
    }

    @Test
    void shouldBeEqualIfNamesAreSame() {
        // Arrange
        Customer customer1 = new Customer(1L, CUSTOMER_NAME);
        Customer customer2 = new Customer(2L, CUSTOMER_NAME);

        // Assert
        assertThat(customer1).isEqualTo(customer2);
    }

    @Test
    void shouldHaveSameHashCodeIfNamesAreSame() {
        // Arrange
        Customer customer1 = new Customer(1L, CUSTOMER_NAME);
        Customer customer2 = new Customer(2L, CUSTOMER_NAME);

        // Assert
        assertThat(customer1.hashCode()).hasSameHashCodeAs(customer2.hashCode());
    }

    @Test
    void shouldNotBeEqualIfNamesAreDifferent() {
        // Arrange
        Customer customer1 = new Customer(1L, CUSTOMER_NAME);
        Customer customer2 = new Customer(2L, "Snacks");

        // Assert
        assertThat(customer1).isNotEqualTo(customer2);
    }
}