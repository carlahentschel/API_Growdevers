package br.com.growdev.growdevers.dtos;

import br.com.growdev.growdevers.enums.EStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateGrowdever(
        String name,
        String phone,
        @Email
        String email,
        EStatus status
) {
}
