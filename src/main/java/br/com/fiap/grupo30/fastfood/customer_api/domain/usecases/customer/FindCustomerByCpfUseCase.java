package br.com.fiap.grupo30.fastfood.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.InvalidCpfException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindCustomerByCpfUseCaseTest {

    private FindCustomerByCpfUseCase findCustomerByCpfUseCase;
    private CustomerGateway customerGateway;

    @BeforeEach
    void setUp() {
        findCustomerByCpfUseCase = new FindCustomerByCpfUseCase();
        customerGateway = mock(CustomerGateway.class);
    }

    @Test
    void shouldThrowInvalidCpfExceptionWhenCpfIsInvalid() {
        String invalidCpf = "12345678900";

        assertThrows(InvalidCpfException.class, () -> {
            findCustomerByCpfUseCase.execute(customerGateway, invalidCpf);
        });
    }

    @Test
    void shouldReturnCustomerDTOWhenCpfIsValidAndCustomerIsFound() {
        String validCpf = "123.456.789-09";
        CustomerDTO expectedCustomerDTO = new CustomerDTO();
        when(customerGateway.findCustomerByCpf(CPF.removeNonDigits(validCpf))).thenReturn(expectedCustomerDTO);

        CustomerDTO actualCustomerDTO = findCustomerByCpfUseCase.execute(customerGateway, validCpf);

        assertEquals(expectedCustomerDTO, actualCustomerDTO);
        verify(customerGateway, times(1)).findCustomerByCpf(CPF.removeNonDigits(validCpf));
    }

    @Test
    void shouldReturnNullWhenCpfIsValidAndCustomerIsNotFound() {
        String validCpf = "123.456.789-09";
        when(customerGateway.findCustomerByCpf(CPF.removeNonDigits(validCpf))).thenReturn(null);

        CustomerDTO actualCustomerDTO = findCustomerByCpfUseCase.execute(customerGateway, validCpf);

        assertNull(actualCustomerDTO);
        verify(customerGateway, times(1)).findCustomerByCpf(CPF.removeNonDigits(validCpf));
    }
}
