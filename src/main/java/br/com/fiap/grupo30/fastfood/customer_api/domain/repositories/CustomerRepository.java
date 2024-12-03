package br.com.fiap.grupo30.fastfood.customer_api.domain.repositories;

import br.com.fiap.grupo30.fastfood.customer_api.domain.entities.Customer;

public interface CustomerRepository {
    Customer findCustomerByCpf(String cpf);

    Customer save(Customer dto);
}
