package io.github.devopMarkz.auth_server.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.devopMarkz.auth_server.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${secret-key}")
    private String secretKey;

    public String obterToken(Usuario usuario) {
        return gerarToken(usuario);
    }

    private String gerarToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer("auth-server-api")
                .withSubject(usuario.getEmail())
                .withClaim("Role", usuario.getPerfil().name())
                .withExpiresAt(Instant.now().plus(8, ChronoUnit.HOURS))
                .sign(algorithm);
    }

//    public String validarTokenERetornarEmail(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(secretKey);
//        return JWT.require(algorithm)
//                .withIssuer("auth-server-api")
//                .build()
//                .verify(token)
//                .getSubject();
//    }

    public Instant obterDataExpiracao(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.require(algorithm)
                .withIssuer("auth-server-api")
                .build()
                .verify(token)
                .getExpiresAtAsInstant();
    }

}
