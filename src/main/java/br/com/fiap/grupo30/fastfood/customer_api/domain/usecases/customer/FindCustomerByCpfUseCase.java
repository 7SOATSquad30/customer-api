package br.com.fiap.grupo30.fastfood.customer_api.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.customer_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.customer_api.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.InvalidCpfException;

import org.springframework.stereotype.Component;

@Component
public class FindCustomerByCpfUseCase {

    public CustomerDTO execute(CustomerGateway customerGateway, String cpf) {
        if (!CPF.isValid(cpf)) {
            throw new InvalidCpfException(cpf);
        }

        return customerGateway.findCustomerByCpf(CPF.removeNonDigits(cpf)).toDTO();
    }
}
