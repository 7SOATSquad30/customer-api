package br.com.fiap.grupo30.fastfood.customer_api.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.customer_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.InvalidCpfException;

import org.springframework.stereotype.Component;

@Component
public class RegisterNewCustomerUseCase {

    public CustomerDTO execute(
            CustomerGateway customerGateway, String name, String cpf, String email) {
        if (!CPF.isValid(cpf)) {
            throw new InvalidCpfException(cpf);
        }

        Customer customer = Customer.create(name, cpf, email);
        return customerGateway.save(customer).toDTO();
    }
}
