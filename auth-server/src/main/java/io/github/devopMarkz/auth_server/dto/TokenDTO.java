package io.github.devopMarkz.auth_server.dto;

public record TokenDTO(
        String access_token,
        String token_type,
        String expires_in,
        String refresh_token
) {
}
