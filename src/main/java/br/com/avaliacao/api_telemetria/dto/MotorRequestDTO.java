package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO que recebe os valores para cadastrar Motor
 * @param setorId Id do setor (opcional)
 * @param codigoAtivo Codigo do ativo
 * @param fabricante nome do fabricante do motor
 * @param modelo modelo do motor
 * @param potenciaKw Potencia do motor em kw
 * @param rpmNominal RPM nominal do motor
 * @param dataInstalacao Data de instalação do motor
 * @param statusAtual indica o status do motor
 */
public record MotorRequestDTO(
        @Schema(description = "Id do setor (opcional)", example = "1")
        Integer setorId,

        @Schema(description = "Codigo do ativo", example = "MOT-USI-001")
        @NotBlank(message = "O código do ativo é obrigatório")
        @Size(max = 50, message = "O código do ativo deve ter no máximo 50 caracteres")
        String codigoAtivo,

        @Schema(description = "nome do fabricante do motor", example = "WEG")
        String fabricante,
        @Schema(description = "modelo do motor", example = "W22 Premium")
        String modelo,

        @Schema(description = "Potencia do motor em kw", example = "15.00")
        @Positive(message = "A potência deve ser um valor positivo")
        BigDecimal potenciaKw,

        @Schema(description = "RPM nominal do motor", example = "4000")
        @Positive(message = "O RPM nominal deve ser um valor positivo")
        Integer rpmNominal,

        @Schema(description = "Data de instalação do motor", example = "2020-02-02")
        LocalDate dataInstalacao,
        @Schema(description = "indica o status do motor", example = "EM_OPERACAO")
        String statusAtual
) {
}
