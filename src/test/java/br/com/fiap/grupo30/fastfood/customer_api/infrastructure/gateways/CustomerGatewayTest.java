package br.com.fiap.grupo30.fastfood.customer_api.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.customer_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.persistence.repositories.JpaCustomerRepository;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.ResourceConflictException;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.ResourceNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

class CustomerGatewayTest {

    @InjectMocks private CustomerGateway customerGateway;

    @Mock private JpaCustomerRepository jpaCustomerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindCustomerByCpf_Found() {
        String cpf = "77503989025";
        CustomerEntity mockEntity = new CustomerEntity(1L, "John Doe", cpf, "johndoe@example.com");
        when(jpaCustomerRepository.findCustomerByCpf(anyString()))
                .thenReturn(Optional.of(mockEntity));

        Customer result = customerGateway.findCustomerByCpf(cpf);

        assertNotNull(result, "Must not be null");
        assertEquals(mockEntity.getCpf(), result.getCpf().value(), "CPF must match");
        verify(jpaCustomerRepository, times(1)).findCustomerByCpf(cpf);
    }

    @Test
    void testFindCustomerByCpf_NotFound() {
        String cpf = "77503989025";
        when(jpaCustomerRepository.findCustomerByCpf(anyString())).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> customerGateway.findCustomerByCpf(cpf),
                "Must throw ResourceNotFoundException");
        verify(jpaCustomerRepository, times(1)).findCustomerByCpf(cpf);
    }

    @Test
    void testSaveCustomer_Success() {
        Customer customer =
                new Customer(1L, "John Doe", new CPF("77503989025"), "johndoe@example.com");
        CustomerEntity mockEntity =
                new CustomerEntity(
                        1L, customer.getName(), customer.getCpf().value(), customer.getEmail());
        when(jpaCustomerRepository.save(any(CustomerEntity.class))).thenReturn(mockEntity);

        Customer result = customerGateway.save(customer);

        assertNotNull(result, "Must not be null");
        assertEquals(customer.getCpf().value(), result.getCpf().value(), "CPF must match");
        verify(jpaCustomerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void testSaveCustomer_Conflict() {
        Customer customer =
                new Customer(1L, "Jane Doe", new CPF("77503989025"), "janedoe@example.com");
        when(jpaCustomerRepository.save(any(CustomerEntity.class)))
                .thenThrow(new DataIntegrityViolationException("CPF already exists"));

        assertThrows(
                ResourceConflictException.class,
                () -> customerGateway.save(customer),
                "Must throw ResourceConflictException");
        verify(jpaCustomerRepository, times(1)).save(any(CustomerEntity.class));
    }
}
