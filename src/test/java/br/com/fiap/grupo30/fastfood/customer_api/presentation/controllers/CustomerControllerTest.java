package br.com.fiap.grupo30.fastfood.customer_api.presentation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        assertEquals(200, response.getStatusCodeValue(), "Must be status OK");
        assertEquals(mockCustomer, response.getBody(), "Must be expected response");

        verify(findCustomerByCpfUseCase, times(1)).execute(any(CustomerGateway.class), eq(cpf));
    }

    //     @Test
    //     void testCreateCustomer() {
    //         // Configuração do mock
    //         CustomerDTO inputCustomer =
    //                 new CustomerDTO("Jane Doe", "77503989025", "janedoe@example.com");
    //         CustomerDTO createdCustomer =
    //                 new CustomerDTO("Jane Doe", "77503989025", "janedoe@example.com");

    //         // Mock do método execute
    //         when(registerNewCustomerUseCase.execute(
    //                         any(CustomerGateway.class), anyString(), anyString(), anyString()))
    //                 .thenReturn(createdCustomer);

    //         // Execução do método do controller
    //         ResponseEntity<CustomerDTO> response =
    // customerController.createCustomer(inputCustomer);

    //         // Verificação
    //         assertEquals(201, response.getStatusCodeValue());
    //         assertEquals(createdCustomer, response.getBody());

    //         verify(registerNewCustomerUseCase, times(1))
    //                 .execute(any(CustomerGateway.class), anyString(), anyString(), anyString());
    //     }
}
