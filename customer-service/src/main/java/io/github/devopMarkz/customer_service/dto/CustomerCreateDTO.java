package io.github.devopMarkz.customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerCreateDTO(
        @NotBlank(message = "Nome precisa ser preenchido.")
        String name,

        @NotBlank(message = "E-mail precisa ser preenchido.")
        @Email(message = "Formato de e-mail incorreto.")
        String email,

        @NotBlank(message = "Telefone precisa ser preenchido.")
        @Pattern(regexp = "^[0-9]+$", message = "Somente números são permitidos.")
        String phoneNumber,

        @NotBlank(message = "Endereço precisa ser preenchido.")
        String address
) {
}
