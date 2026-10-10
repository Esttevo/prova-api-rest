package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Dados da telemetria expostos pela API
 * @param id identificador da telemetria
 * @param motorId id do motor relacionando a telemetria
 * @param dataHora Hora em que foi gerada a telemetria
 * @param temperaturaCarcaca temperatura da carcaça do motor
 * @param rpmAtual rpm atual do motor
 * @param vibracaoGlobal vibração global do motor
 */
@Schema(description = "Dados da telemetria expostos pela API")
public record TelemetriaResponseDTO(
        @Schema(description = "identificador da telemetria", example = "1")
        Long id,
        @Schema(description = "id do motor relacionando a telemetria", example = "1")
        Integer motorId,
        @Schema(description = "Hora em que foi gerada a telemetria ", example = "2026-10-07 08:00:00.000000")
        LocalDateTime dataHora,
        @Schema(description = "temperatura da carcaça do motor", example = "50")
        BigDecimal temperaturaCarcaca,
        @Schema(description = "rpm atual do motor")
        Integer rpmAtual,
        @Schema(description = "vibração global do motor", example = "2.30")
        BigDecimal vibracaoGlobal
) {
}
