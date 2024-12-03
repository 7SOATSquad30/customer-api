package br.com.fiap.grupo30.fastfood.customer_api.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.customer_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.InvalidCpfException;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindCustomerByCpfUseCaseTest {

    @InjectMocks
    private FindCustomerByCpfUseCase findCustomerByCpfUseCase;

    @Mock
    private CustomerGateway customerGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ValidCpf() {
        // Dados simulados
        String validCpf = "12345678900";
        Customer mockCustomer = new Customer(1L, "John Doe", new CPF(validCpf), "johndoe@example.com");
        CustomerDTO expectedDTO = new CustomerDTO("John Doe", validCpf, "johndoe@example.com");

        // Configuração do mock
        when(customerGateway.findCustomerByCpf(validCpf)).thenReturn(mockCustomer);

        // Execução do método
        CustomerDTO result = findCustomerByCpfUseCase.execute(customerGateway, validCpf);

        // Verificações
        assertNotNull(result);
        assertEquals(expectedDTO.getName(), result.getName());
        assertEquals(expectedDTO.getCpf(), result.getCpf());
        assertEquals(expectedDTO.getEmail(), result.getEmail());
        verify(customerGateway, times(1)).findCustomerByCpf(validCpf);
    }

    @Test
    void testExecute_InvalidCpf() {
        String invalidCpf = "123";

        // Verifica se a exceção é lançada para um CPF inválido
        assertThrows(InvalidCpfException.class, () -> {
            findCustomerByCpfUseCase.execute(customerGateway, invalidCpf);
        });

        verifyNoInteractions(customerGateway);
    }

    @Test
    void testExecute_CustomerNotFound() {
        String validCpf = "12345678900";

        // Configuração para simular um cliente não encontrado
        when(customerGateway.findCustomerByCpf(validCpf)).thenThrow(new ResourceNotFoundException("Customer not found"));

        // Verifica se a exceção correta é lançada
        assertThrows(ResourceNotFoundException.class, () -> {
            findCustomerByCpfUseCase.execute(customerGateway, validCpf);
        });

        verify(customerGateway, times(1)).findCustomerByCpf(validCpf);
    }
}
