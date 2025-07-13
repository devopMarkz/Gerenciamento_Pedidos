package io.github.devopMarkz.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class CorsGlobalConfiguration {

    @Bean
    public WebFilter corsFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = exchange.getResponse();
                HttpHeaders headers = response.getHeaders();

                headers.add("Access-Control-Allow-Origin", "http://127.0.0.7:5500"); // ou o domínio do seu front
                headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                headers.add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
                headers.add("Access-Control-Expose-Headers", "Authorization");
                headers.add("Access-Control-Allow-Credentials", "true");

                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }

            return chain.filter(exchange);
        };
    }
}