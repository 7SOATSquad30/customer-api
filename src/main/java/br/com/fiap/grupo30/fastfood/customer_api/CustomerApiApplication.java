package br.com.fiap.grupo30.fastfood.customer_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Customer API", version = "v1"),
        servers = {
            @Server(url = "http://localhost:8080", description = "Local Development"),
            @Server(
                    url = "https://29glms05ff.execute-api.us-east-1.amazonaws.com",
                    description = "Production Server")
        })
public class CustomerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApiApplication.class, args);
    }
}
