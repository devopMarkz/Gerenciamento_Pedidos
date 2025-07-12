package io.github.devopMarkz.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> {
                    return route.path("/cliente-service/**")
                            .filters(f -> f.stripPrefix(1))
                            .uri("lb://cliente-service");
                })
                .route(route -> {
                    return route
                            .path("/pedido-service/**")
                            .filters(f -> f.stripPrefix(1))
                            .uri("lb://pedido-service");
                })
                .route(route -> {
                    return route
                            .path("/produto-service/**")
                            .filters(f -> f.stripPrefix(1))
                            .uri("lb://produto-service");
                })
                .build();
    }

}
