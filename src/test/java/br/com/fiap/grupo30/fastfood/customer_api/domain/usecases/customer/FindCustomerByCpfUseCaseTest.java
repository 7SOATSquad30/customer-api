package br.com.fiap.grupo30.fastfood.customer_api.domain.usecases.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.customer_api.utils.CustomerHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FindCustomerByCpfUseCaseTest {

    @Mock private CustomerGateway customerGateway;

    @InjectMocks private FindCustomerByCpfUseCase findCustomerByCpfUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCorrectCustomerName() {
        // Arrange
        Long validId = 1L;
        Customer customer = CustomerHelper.createDefaultCustomerWithId(validId);
        when(customerGateway.findById(validId)).thenReturn(customer);

        // Act
        CustomerDTO result = findCustomerByCpfUseCase.execute(customerGateway, validId);

        // Assert
        assertThat(result.getName()).isEqualTo(customer.getName());
    }

    @Test
    void shouldCallFindByIdOnCustomerGateway() {
        // Arrange
        Long customerId = 1L;
        Customer customer = CustomerHelper.createDefaultCustomerWithId(customerId);
        when(customerGateway.findById(customerId)).thenReturn(customer);

        // Act
        findCustomerByCpfUseCase.execute(customerGateway, customerId);

        // Assert
        verify(customerGateway).findById(customerId);
    }
}