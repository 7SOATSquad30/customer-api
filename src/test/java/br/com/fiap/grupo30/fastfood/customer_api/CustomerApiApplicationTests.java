package br.com.fiap.grupo30.fastfood.customer_api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerApiApplicationTests {

    @Test
    void applicationStarts() {
        // Verifica se a aplicação inicia sem erros
        CustomerApiApplication.main(new String[] {});
    }
    
}
