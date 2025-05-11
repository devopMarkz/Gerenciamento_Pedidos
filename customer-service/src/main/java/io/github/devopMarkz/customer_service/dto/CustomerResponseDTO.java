package io.github.devopMarkz.customer_service.dto;

public record CustomerResponseDTO(
        Long id,
        String name,
        String email,
        String phoneNumber,
        String address
) {
}
