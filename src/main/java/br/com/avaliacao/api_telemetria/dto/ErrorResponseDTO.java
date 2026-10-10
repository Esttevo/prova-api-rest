package br.com.avaliacao.api_telemetria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * dados do erro expostos pela API
 * @param timestamp Momento que ocorreu
 * @param status codigo de status do erro
 * @param error tipo do erro
 * @param message Mensagem do erro lançado
 * @param fieldErrors Todos as mensagens detalhadas do erro
 */
@Schema(description = "expõe o erro de forma mais entendivel para fora da API")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
        @Schema(description ="Momento que ocorreu", example = "2026-10-09T18:56:24.637755")
        LocalDateTime timestamp,
        @Schema(description = "codigo de status do erro", example = "404")
        Integer status,
        @Schema(description = "tipo do erro lançado", example = "Recurso não encontrado")
        String error,
        @Schema(description = "Mensagem do erro lançado", example = "Motor não encontrado para o ID: 100")
        String message,
        @Schema (description = "Todos as mensagens detalhadas do erro", example = "O tipo de anomalia é obrigatório")
        Map<String, String> fieldErrors
) {
    /**
     * metodo construtor sem o field Errors
     * @param status codigo de status do erro
     * @param error tipo do erro
     * @param message Mensagem do erro lançado
     */
    public ErrorResponseDTO(Integer status, String error, String message) {
        this(LocalDateTime.now(), status, error, message, null);
    }
    /**
     * metodo construtor com o field errors
     * @param status codigo de status do erro
     * @param error tipo do erro
     * @param message Mensagem do erro lançado
     * @param fieldErrors mensagens detalhadas de todos os erros
     */
    public ErrorResponseDTO(Integer status, String error, String message, Map<String, String> fieldErrors) {
        this(LocalDateTime.now(), status, error, message, fieldErrors);
    }
}
