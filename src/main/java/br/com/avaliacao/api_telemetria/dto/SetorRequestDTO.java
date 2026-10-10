package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Dados pedidos pela api para cadastrar Setor
 * @param nome nome do setor
 * @param localizacao localidade do setor
 */
@Schema(description = "Dados pedidos pela api para cadastrar Setor")
public record SetorRequestDTO (
        @Schema(description = "Nome do setor", example = "Ferramentaria")
        @NotBlank(message = "O nome do setor é obrigatório")
        @Size(max = 100, message = "O nome do setor deve ter no máximo 100 caracteres")
        String nome,

        @Schema(description = "localidade do setor", example = "Parque Fabril C")
        @Size(max = 100, message = "A localização deve ter no máximo 100 caracteres")
        String localizacao
){

}
