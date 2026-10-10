package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Threshold;

/**
 * dto que pede o status do motor para fazer a atualização do mesmo
 * @param statusAtual status atual do motor
 */
@Schema(description = "Dto que pede o status atual do motor para fazer atualização")

public record MotorStatusUpdateDTO(
        @Schema(description = "Status do motor", example = "EM_OPERACAO")
        @NotBlank(message = "O status atual é obrigatório")
        String statusAtual
) {
}
