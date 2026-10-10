package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Dados de motor expostos pela API
 * @param id identificador do motor
 * @param setorId Id do setor
 * @param setorNome nome do setor
 * @param codigoAtivo Codigo do ativo
 * @param fabricante nome do fabricante do motor
 * @param modelo modelo do motor
 * @param potenciaKw Potencia do motor em kw
 * @param rpmNominal RPM nominal do motor
 * @param dataInstalacao Data de instalação do motor
 * @param statusAtual indica o status do motor
 */
@Schema(description = "Dados de motor expostos pela API")
public record MotorResponseDTO(
        @Schema(description = "identificador do motor", example = "1")
        Integer id,

        @Schema(description = "Id do setor ", example = "1")
        Integer setorId,

        @Schema(description = "nome do setor", example = "ferramentaria")
        String setorNome,

        @Schema(description = "Codigo do ativo", example = "MOT-USI-001")
        String codigoAtivo,

        @Schema(description = "nome do fabricante do motor", example = "WEG")
        String fabricante,

        @Schema(description = "modelo do motor", example = "W22 Premium")
        String modelo,

        @Schema(description = "Potencia do motor em kw", example = "15.00")
        BigDecimal potenciaKw,

        @Schema(description = "RPM nominal do motor", example = "4000")
        Integer rpmNominal,

        @Schema(description = "Data de instalação do motor", example = "2020-02-02")
        LocalDate dataInstalacao,

        @Schema(description = "indica o status do motor", example = "EM_OPERACAO")
        String statusAtual
) {
}
