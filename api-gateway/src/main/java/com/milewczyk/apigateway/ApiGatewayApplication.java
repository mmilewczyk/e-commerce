package com.milewczyk.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    RouteLocator gateway (RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route( routeSpec -> routeSpec
                        .path("/products")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.setPath("/web/products"))
                        .uri("http://localhost:8080")
                )
                .route( routeSpec -> routeSpec
                        .path("/admin/products")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.setPath("/admin/products"))
                        .uri("http://localhost:8080")
                )
                .build();
    }
}
