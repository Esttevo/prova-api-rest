package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Dados pedidos pela api para cadastro de alerta
 * @param motorId Id do motor
 * @param tipoAnomalia Tipo da anomalia
 * @param criticidade criticidade do alerta
 * @param descricao Descricao detalahada do alerta, (opcional)
 */
@Schema(
        description = "Dados pedidos pela api para cadastro de alerta"
)
public record AlertaRequestDTO(
        @Schema(description = "Id do motor",
        example = "1")
        @NotNull(message = "O ID do motor é obrigatório")
        Integer motorId,

        @Schema(description = "Tipo da anomalia",
        example = "Fazendo barulho")
        @NotBlank(message = "O tipo de anomalia é obrigatório")
        @Size(max = 100, message = "O tipo de anomalia deve ter no máximo 100 caracteres")
        String tipoAnomalia,

        @Schema(description = "criticidade do alerta", example = "MUITO_CRITICO")
        @NotBlank(message = "A criticidade é obrigatória")
        @Size(max = 20, message = "A criticidade deve ter no máximo 20 caracteres")
        String criticidade,

        @Schema(description = "Descricao detalahada do alerta, (opcional)", example = "ao ligar faz baruhlos estranhos")
        String descricao
) {
}
