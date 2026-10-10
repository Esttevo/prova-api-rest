package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Dto que recebe o status do alerta para atualização
 * @param resolvido true ou false
 */
@Schema(description = "Dto que recebe o status do alerta para atualização")
public record AlertaResolucaoDTO(
        @Schema(description = "indica se foi resolvido ou não", example = "true")
        @NotNull(message = "O status de resolução é obrigatório")
        Boolean resolvido
) {
}
