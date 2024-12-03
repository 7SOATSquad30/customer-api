package br.com.fiap.grupo30.fastfood.customer_api.presentation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.customer_api.domain.usecases.customer.FindCustomerByCpfUseCase;
import br.com.fiap.grupo30.fastfood.customer_api.domain.usecases.customer.RegisterNewCustomerUseCase;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.persistence.repositories.JpaCustomerRepository;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class CustomerControllerTest {

    @InjectMocks private CustomerController customerController;

    @Mock private FindCustomerByCpfUseCase findCustomerByCpfUseCase;

    @Mock private RegisterNewCustomerUseCase registerNewCustomerUseCase;

    @Mock private JpaCustomerRepository jpaCustomerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindCustomerByCpf() {
        // Configuração do mock
        String cpf = "77503989025";
        CustomerDTO mockCustomer = new CustomerDTO("John Doe", cpf, "johndoe@example.com");

        // Mock do método execute
        when(findCustomerByCpfUseCase.execute(any(CustomerGateway.class), eq(cpf)))
                .thenReturn(mockCustomer);

        // Execução do método do controller
        ResponseEntity<CustomerDTO> response = customerController.findCustomerByCpf(cpf);

        // Verificação
        // assertEquals(200, response.getStatusCodeValue(), "Must be status OK");
        assertEquals(mockCustomer, response.getBody(), "Must be expected response");
    }

    @Test
    void testFindCustomerByCpf_Success() {
        // Configuração do mock
        String cpf = "77503989025";
        CustomerDTO mockCustomer = new CustomerDTO("John Doe 2", cpf, "johndoe2@example.com");

        when(findCustomerByCpfUseCase.execute(any(CustomerGateway.class), eq(cpf)))
                .thenReturn(mockCustomer);

        // Execução do método do controller
        ResponseEntity<CustomerDTO> response = customerController.findCustomerByCpf(cpf);

        // Verificação
        assertEquals(mockCustomer, response.getBody(), "Expected customer data");
    }

    @Test
    void testCreateCustomer_Failure() {
        // Dados de entrada válidos
        CustomerDTO inputCustomer =
                new CustomerDTO("Jane Doe 4", "47796327064", "janedoe4@example.com");

        // Simulando falha no método execute
        when(registerNewCustomerUseCase.execute(
                        any(CustomerGateway.class),
                        eq(inputCustomer.getName()),
                        eq(inputCustomer.getCpf()),
                        eq(inputCustomer.getEmail())))
                .thenThrow(new RuntimeException("Error creating customer"));

        // Execução do método do controller
        try {
            customerController.createCustomer(inputCustomer);
        } catch (RuntimeException e) {
            // Verificação
            assertEquals(
                    "Error creating customer",
                    e.getMessage(),
                    "Expected error message from service");
        }
    }
}
