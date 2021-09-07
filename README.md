# 🛒E-commerce microservices app
An e-commerce application designed as microservices. The application allows you to browse the catalog with products, add products to the cart and compose orders.

### ⚠️ The project is at a very early stage, there are no tests yet. Many functionalities have not yet been developed 100%.

Documentation available at:
```
Each service has Swagger documentation

http://localhost:port/swagger-ui.html
http://localhost:port/v2/api-docs
```

 ## Configuration Server
 https://github.com/agiklo/ecommerce-config

## Technologies
- Java 11
- Spring (Boot, Data, Security, Cloud, Actuator, Bus)
- Netflix Eureka Discovery
- Netflix Hystrix Circuit Breaker
- RabbitMQ
- Hibernate
- PostgreSQL
- MongoDB
- OAuth2
- Keycloak
- JWT
- Lombok
- MapStruct
```
⚠️⚠️⚠️
Remember to install the Lombok plugin in your IDE, more information in the link below:
github.com/mplushnikov/lombok-intellij-plugin#plugin-installation
```

## Service ports
- localhost:8080 - PRODUCT-CATALOG-SERVICE
- localhost:8081 - PRODUCT-INFO-SERVICE
- localhost:8082 - USER-SERVICE
- localhost:8083 - CART-SERVICE
- localhost:8084 - ORDER-SERVICE
- localhost:8086 - API-GATEWAY
- localhost:8761 - DISCOVERY-SERVICE (EUREKA)
- localhost:8888 - CONFIG-SERVER
- localhost:9090 - ADMIN-SERVER

## RabbitMQ Configuration
```
The Docker-compose that creates RabbitMQ is located at:
e-commerce/api-gateway/docker-compose.yaml

Configuration is located at:
e-commerce/api-gateway/src/main/resources/application.yml
```

