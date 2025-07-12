package io.github.devopMarkz.auth_server.controllers;

import io.github.devopMarkz.auth_server.dto.LoginDTO;
import io.github.devopMarkz.auth_server.dto.TokenDTO;
import io.github.devopMarkz.auth_server.model.Usuario;
import io.github.devopMarkz.auth_server.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> autenticar(@RequestBody LoginDTO loginDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
        var auth = authenticationManager.authenticate(authenticationToken);
        Usuario usuario = (Usuario) auth.getPrincipal();
        String token = tokenService.obterToken(usuario);
        Instant expiresAt = tokenService.obterDataExpiracao(token);
        return ResponseEntity.ok(new TokenDTO(token, "Bearer", expiresAt.toString(), null));
    }

}
