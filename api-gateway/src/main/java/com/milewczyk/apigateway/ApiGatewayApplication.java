package com.milewczyk.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.event.RefreshRoutesResultEvent;
import org.springframework.cloud.gateway.route.CachingRouteLocator;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    ApplicationListener<RefreshRoutesResultEvent> routesRefreshed() {
        return refreshRoutesResultEvent -> {
            log.info("Routes updated");
            var crl = (CachingRouteLocator) refreshRoutesResultEvent.getSource();
            Flux<Route> routes = crl.getRoutes();
            routes.subscribe(System.out::println);
        };
    }

    @Bean
    RouteLocator gateway(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(routeSpec -> routeSpec
                        .path("/products")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.setPath("/web/products"))
                        .uri("lb://PRODUCT-CATALOG-SERVICE")
                )
                .route(routeSpec -> routeSpec
                        .path("/admin/products")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.setPath("/admin/products"))
                        .uri("lb://PRODUCT-CATALOG-SERVICE")
                )
                .route(routeSpec -> routeSpec
                        .path("/products/{id}")
                        .filters(fs -> fs.circuitBreaker(cbc ->
                                cbc.setFallbackUri("forward:/PRODUCT-CATALOG-SERVICE"))
                        )
                        .uri("lb://PRODUCT-INFO-SERVICE")
                )
                .build();
    }
}
