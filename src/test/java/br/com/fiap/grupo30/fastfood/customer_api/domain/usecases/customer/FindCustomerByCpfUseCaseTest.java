package br.com.fiap.grupo30.fastfood.customer_api.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.customer_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.InvalidCpfException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FindCustomerByCpfUseCaseTest {

    @InjectMocks private FindCustomerByCpfUseCase findCustomerByCpfUseCase;

    @Mock private CustomerGateway customerGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ValidCpf() {
        // Dados simulados
        String validCpf = "77503989025";
        Customer mockCustomer =
                new Customer(1L, "John Doe", new CPF(validCpf), "johndoe@example.com");
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
        assertThrows(
                InvalidCpfException.class,
                () -> {
                    findCustomerByCpfUseCase.execute(customerGateway, invalidCpf);
                });

        verifyNoInteractions(customerGateway);
    }
}
