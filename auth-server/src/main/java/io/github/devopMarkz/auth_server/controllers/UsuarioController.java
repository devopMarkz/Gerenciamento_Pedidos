package io.github.devopMarkz.auth_server.controllers;

import io.github.devopMarkz.auth_server.model.Usuario;
import io.github.devopMarkz.auth_server.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody Usuario usuario) {
        var usuarioSalvo = usuarioService.save(usuario);
        return ResponseEntity.ok().build();
    }
}
