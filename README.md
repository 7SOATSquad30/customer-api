# Customer-API

Microservices that provides all necessary endpoints.

## Test evidence

<img src="/tests.jpg">


## Como rodar

Instalar dependências e buildar
```
./gradlew build --refresh-dependencies
```

Subir infra local
```
docker-compose up -d dynamodb aws-cli
```

Rodar aplicação
```
./gradlew bootRun
```

Swagger: http://localhost:8080/swagger-ui/index.html#/