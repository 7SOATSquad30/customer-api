package br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InvalidCpfExceptionTest {

    @Test
    void testInvalidCpfException_Message() {
        // Dados de entrada
        String cpf = "77503989025";

        // Criação da exceção
        InvalidCpfException exception = new InvalidCpfException(cpf);

        // Verificação da mensagem
        String expectedMessage = String.format("Invalid CPF: %s", cpf);
        assertEquals(
                expectedMessage,
                exception.getMessage(),
                "The exception message should be formatted correctly");
    }

    @Test
    void testInvalidCpfException_WithCause() {
        // Dados de entrada e causa
        String cpf = "77503989025";
        Throwable cause = new RuntimeException("Cause of the error");

        // Criação da exceção com a causa
        InvalidCpfException exception = new InvalidCpfException(cpf, cause);

        // Verificação da causa
        assertEquals(
                cause,
                exception.getCause(),
                "The cause of the exception should be the same as the passed one");
    }

    @Test
    void testInvalidCpfException_EmptyCpf() {
        // Dados de entrada com CPF vazio
        String cpf = "";

        // Criação da exceção
        InvalidCpfException exception = new InvalidCpfException(cpf);

        // Verificação da mensagem
        String expectedMessage = String.format("Invalid CPF: %s", cpf);
        assertEquals(
                expectedMessage,
                exception.getMessage(),
                "The exception message should handle empty CPF correctly");
    }
}
