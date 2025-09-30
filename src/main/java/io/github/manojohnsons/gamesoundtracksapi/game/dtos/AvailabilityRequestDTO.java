package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AvailabilityRequestDTO {
    
    @NotBlank
    @URL(message = "A URL de compra fornecida é inválida.")
    private String purchaseUrl;
}
