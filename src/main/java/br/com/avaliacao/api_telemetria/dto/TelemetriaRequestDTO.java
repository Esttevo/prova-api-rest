package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Recebe os dados de telemetria para o cadastro da mesma
 * @param motorId id do motor relacionando a telemetria
 * @param temperaturaCarcaca temperatura da carcaça do motor
 * @param rpmAtual rpm atual do motor
 * @param vibracaoGlobal vibração global do motor
 */
@Schema(description = "Recebe os dados de telemetria para o cadastro da mesma")
public record TelemetriaRequestDTO(
        @Schema(description = "id do motor relacionando a telemetria", example = "1")
        @NotNull(message = "O ID do motor é obrigatório")
        Integer motorId,

        @Schema(description = "temperatura da carcaça do motor", example = "50")
        @NotNull(message = "A temperatura é obrigatória")
        BigDecimal temperaturaCarcaca,

        @Schema(description = "rpm atual do motor")
        @NotNull(message = "O RPM é obrigatório")
        Integer rpmAtual,

        @Schema(description = "vibração global do motor", example = "2.30")
        @NotNull(message = "A vibração é obrigatória")
        BigDecimal vibracaoGlobal
) {
}
