package br.com.growdev.growdevers.dtos;

import br.com.growdev.growdevers.enums.EStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public record CreateGrowdeverDTO(
        @NotBlank
        @Length(min = 3, max = 30)
        String name,
        String phone,
        @NotBlank
        @CPF
        String cpf,
        @NotBlank
        @Email
        String email,
        @NotNull
        EStatus status
) {
}
