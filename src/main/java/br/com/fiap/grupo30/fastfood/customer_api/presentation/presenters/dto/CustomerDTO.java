package br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "Campo obrigatório")
    private String name;

    @NotBlank(message = "Campo obrigatório")
    private String cpf;

    @Email(message = "Favor entrar um email válido")
    private String email;
}
