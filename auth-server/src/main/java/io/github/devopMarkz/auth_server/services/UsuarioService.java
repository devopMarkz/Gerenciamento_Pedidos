package io.github.devopMarkz.auth_server.services;

import io.github.devopMarkz.auth_server.model.Usuario;
import io.github.devopMarkz.auth_server.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(usuario.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(novoUsuario);
    }

}
