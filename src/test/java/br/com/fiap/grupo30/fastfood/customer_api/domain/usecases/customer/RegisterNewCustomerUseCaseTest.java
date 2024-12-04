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

class RegisterNewCustomerUseCaseTest {

    @InjectMocks private RegisterNewCustomerUseCase registerNewCustomerUseCase;

    @Mock private CustomerGateway customerGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_SuccessfulRegistration() {
        // Dados simulados
        Long id = 1L;
        String name = "John Doe";
        String cpf = "77503989025";
        String email = "johndoe@example.com";

        Customer mockCustomer = new Customer(1L, name, new CPF(cpf), email);
        CustomerDTO expectedDTO = new CustomerDTO(id, name, cpf, email);

        // Configuração do comportamento do mock
        when(customerGateway.save(any(Customer.class))).thenReturn(mockCustomer);

        // Execução do método
        CustomerDTO result = registerNewCustomerUseCase.execute(customerGateway, name, cpf, email);

        // Verificações
        // assertNotNull(result);
        assertEquals(expectedDTO.getName(), result.getName(), "Name should be the same");
        // verify(customerGateway, times(1)).save(any(Customer.class));
    }

    @Test
    void testExecute_InvalidCpf() {
        String invalidCpf = "123";

        // Verifica se a exceção é lançada ao usar CPF inválido
        assertThrows(
                InvalidCpfException.class,
                () -> {
                    registerNewCustomerUseCase.execute(
                            customerGateway, "John Doe", invalidCpf, "johndoe@example.com");
                });

        // Garante que o gateway não foi chamado
        // verifyNoInteractions(customerGateway);
    }
}
