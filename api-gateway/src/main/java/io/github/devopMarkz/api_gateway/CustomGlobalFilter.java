package io.github.devopMarkz.api_gateway;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomGlobalFilter implements GlobalFilter {

    @Value("${secret-key}")
    private String secretKey;

    @Value("${secret-access}")
    private String secretAccess;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return unauth(exchange, "Token invalid");
        }

        String token = authorizationHeader.substring(7);

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("auth-server-api")
                    .build()
                    .verify(token);

            String email = decodedJWT.getSubject();
            String role = decodedJWT.getClaim("Role").asString();

            ServerHttpRequest request = exchange.getRequest()
                    .mutate()
                    .header("X-User-Email", email)
                    .header("X-User-Role", role)
                    .header("X-Secret-Access", secretAccess)
                    .build();

            ServerWebExchange newExchange = exchange.mutate().request(request).build();

            return chain.filter(newExchange);
        } catch (Exception e) {
            return unauth(exchange, "Token invalid");
        }
    }

    private Mono<Void> unauth(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

}
