package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Threshold;

/**
 * Dados de setor expostos pela api
 * @param id identificador do setor
 * @param nome nome do setor
 * @param localizacao localidade do setor
 */
@Schema(description = "Dados de setor expostos pela api")
public record SetorResponseDTO(
        @Schema(description = "Identificador unico de setor", example = "1")
        Integer id,
        @Schema(description = "Nome do setor", example = "Ferramentaria")
        String nome,
        @Schema(description = "localidade do setor", example = "Parque Fabril C")
        String localizacao
) {
}
