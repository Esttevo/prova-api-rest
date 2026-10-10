package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Dados de Alerta motores expostos pela api
 * @param id Id do alerta
 * @param motorId id do motor
 * @param codigoAtivoMotor codigo unico do motor
 * @param dataAlerta Data que o alerta foi emitido
 * @param tipoAnomalia Tipo da anomalia
 * @param criticidade criticidade do alerta
 * @param descricao  descricao do alerta, (opcional)
 * @param resolvido indica se foi resolvido ou não
 */
@Schema(description = "Dados de Alerta motores expostos pela api")
public record AlertaResponseDTO(
        @Schema(description = "Id do alerta", example = "1")
        Integer id,

        @Schema(description = "id do motor", example = "1")
        Integer motorId,
        @Schema(description = "codigo unico do motor", example = "MOT-USI-001")
        String codigoAtivoMotor,
        @Schema(description = "Data que o alerta foi emitido", example = "2023-03-02")
        LocalDateTime dataAlerta,
        @Schema(description = "Tipo da anomalia", example = "Fazendo barulho")
        String tipoAnomalia,
        @Schema(description = "criticidade do alerta", example = "muito critico")
        String criticidade,
        @Schema(description = "descricao do alerta, (opcional)", example = "Faz barulho ao ligar")
        String descricao,
        @Schema(description = "indica se foi resolvido ou não", example = "true")
        Boolean resolvido
) {
}
