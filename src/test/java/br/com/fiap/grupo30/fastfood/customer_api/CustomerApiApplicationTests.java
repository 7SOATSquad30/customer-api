package br.com.fiap.grupo30.fastfood.customer_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerApiApplicationTests {

    @Test
    void contextLoads() {
        int x = 1;
        int y = 1;
        assertEquals(x, y, "assertion failed");
    }
    
}
