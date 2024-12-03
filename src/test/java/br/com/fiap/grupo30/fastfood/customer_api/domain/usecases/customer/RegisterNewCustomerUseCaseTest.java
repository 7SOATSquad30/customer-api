package br.com.fiap.grupo30.fastfood.customer_api.domain.usecases.customer;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Category;
import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.customer_api.utils.CustomerHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RegisterNewCustomerUseCaseTest {

    @Mock private CustomerGateway customerGateway;

    @InjectMocks private RegisterNewCustomerUseCase registerNewCustomerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowInvalidCpfExceptionWhenCpfIsInvalid() {
        String invalidCpf = "12345678900";
        String name = "John Doe";
        String email = "john.doe@example.com";

        assertThrows(InvalidCpfException.class, () -> {
            registerNewCustomerUseCase.execute(customerGateway, name, invalidCpf, email);
        });
    }

    @Test
    void shouldReturnCustomerDTOWhenCpfIsValid() {
        String validCpf = "123.456.789-09";
        String name = "John Doe";
        String email = "john.doe@example.com";
        Customer customer = Customer.create(name, validCpf, email);
        CustomerDTO expectedCustomerDTO = customer.toDTO();

        when(customerGateway.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO actualCustomerDTO = registerNewCustomerUseCase.execute(customerGateway, name, validCpf, email);

        assertEquals(expectedCustomerDTO, actualCustomerDTO);
        verify(customerGateway, times(1)).save(any(Customer.class));
    }
}