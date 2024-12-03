package br.com.fiap.grupo30.fastfood.customer_api.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.persistence.repositories.JpaCustomerRepository;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.ResourceConflictException;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerGatewayTest {

    @InjectMocks
    private CustomerGateway customerGateway;

    @Mock
    private JpaCustomerRepository jpaCustomerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindCustomerByCpf_Found() {
        String cpf = "12345678900";
        CustomerEntity mockEntity = new CustomerEntity(1L, "John Doe", cpf, "johndoe@example.com");
        when(jpaCustomerRepository.findCustomerByCpf(anyString())).thenReturn(Optional.of(mockEntity));

        Customer result = customerGateway.findCustomerByCpf(cpf);

        assertNotNull(result);
        assertEquals(mockEntity.getCpf(), result.getCpf().value());
        verify(jpaCustomerRepository, times(1)).findCustomerByCpf(cpf);
    }

    @Test
    void testFindCustomerByCpf_NotFound() {
        String cpf = "12345678900";
        when(jpaCustomerRepository.findCustomerByCpf(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerGateway.findCustomerByCpf(cpf));
        verify(jpaCustomerRepository, times(1)).findCustomerByCpf(cpf);
    }

    @Test
    void testSaveCustomer_Success() {
        Customer customer = new Customer("John Doe", "12345678900", "johndoe@example.com");
        CustomerEntity mockEntity = new CustomerEntity(1L, customer.getName(), customer.getCpf().value(), customer.getEmail());
        when(jpaCustomerRepository.save(any(CustomerEntity.class))).thenReturn(mockEntity);

        Customer result = customerGateway.save(customer);

        assertNotNull(result);
        assertEquals(customer.getCpf().value(), result.getCpf().value());
        verify(jpaCustomerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void testSaveCustomer_Conflict() {
        Customer customer = new Customer("Jane Doe", "98765432100", "janedoe@example.com");
        when(jpaCustomerRepository.save(any(CustomerEntity.class)))
                .thenThrow(new DataIntegrityViolationException("CPF already exists"));

        assertThrows(ResourceConflictException.class, () -> customerGateway.save(customer));
        verify(jpaCustomerRepository, times(1)).save(any(CustomerEntity.class));
    }
}
