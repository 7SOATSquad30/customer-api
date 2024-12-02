package br.com.fiap.grupo30.fastfood.customer_api.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.domain.repositories.CustomerRepository;
import br.com.fiap.grupo30.fastfood.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.configuration.Constants;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCustomerRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceConflictException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerGatewayTest {

    @Mock private JpaCustomerRepository jpaCustomerRepository;

    @InjectMocks private CustomerGateway customerGateway;

    private static final Long CUSTOMER_ID = 1L;
    private static final String CUSTOMER_NAME = "John";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findCustomerByCpf() {
        // Arrange
        String cpf = "12345678900";
        CustomerEntity customerEntity = new CustomerEntity(CUSTOMER_ID, CUSTOMER_NAME, cpf);
        when(jpaCustomerRepository.findCustomerByCpf(cpf)).thenReturn(Optional.of(customerEntity));

        // Act
        Customer customer = customerGateway.findCustomerByCpf(cpf);

        // Assert
        assertEquals(CUSTOMER_ID, customer.getId());
        assertEquals(CUSTOMER_NAME, customer.getName());
        assertEquals(cpf, customer.getCpf().getValue());
    }

    @Test
    void findCustomerByCpfNotFound() {
        // Arrange
        String cpf = "12345678900";
        when(jpaCustomerRepository.findCustomerByCpf(cpf)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> customerGateway.findCustomerByCpf(cpf));
    }

    @Test
    void findCustomerByCpfAnonymous() {
        // Arrange
        String cpf = Constants.ANONYMOUS_CPF;
        when(jpaCustomerRepository.findCustomerByCpf(cpf)).thenReturn(Optional.empty());

        // Act
        Customer customer = customerGateway.findCustomerByCpf(cpf);

        // Assert
        assertNull(customer);
    }

    @Test
    void save() {
        // Arrange
        String cpf = "12345678900";
        Customer customer = new Customer(CUSTOMER_ID, CUSTOMER_NAME, new CPF(cpf));
        CustomerEntity customerEntity = new CustomerEntity(CUSTOMER_ID, CUSTOMER_NAME, cpf);
        when(jpaCustomerRepository.save(customerEntity)).thenReturn(customerEntity);

        // Act
        Customer savedCustomer = customerGateway.save(customer);

        // Assert
        assertEquals(CUSTOMER_ID, savedCustomer.getId());
        assertEquals(CUSTOMER_NAME, savedCustomer.getName());
        assertEquals(cpf, savedCustomer.getCpf().getValue());
    }

}
