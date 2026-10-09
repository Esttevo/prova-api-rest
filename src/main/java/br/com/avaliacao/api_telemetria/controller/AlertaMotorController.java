package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.AlertaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResolucaoDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResponseDTO;
import br.com.avaliacao.api_telemetria.service.AlertaMotorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


/**
 * Controller que gerencia os endpoints de Alerta Motor
 */
@Tag(name = "Controller Alerta Motores", description = "Possui os endpoints de alerta motores")
@RestController
@RequestMapping("/api/v1/alertas")
public class AlertaMotorController {

    private final AlertaMotorService alertaMotorService;

    public AlertaMotorController(AlertaMotorService alertaMotorService) {
        this.alertaMotorService = alertaMotorService;
    }


    /**
     * Criar Alerta Motor
     * @param alertaRequestDTO request com os dados necessarios para criar motor
     * @return {@link AlertaResponseDTO} com id gerado pelo banco
     */
    @Operation(
            summary = "Criar um alerta motor",
            description = "Cria um alerta de acordo com os valores passados no alertaRequest"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alerta motor criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor", content = @Content)
    })

    @PostMapping()
    public ResponseEntity<AlertaResponseDTO> criar(@Valid @RequestBody AlertaRequestDTO alertaRequestDTO) {
        AlertaResponseDTO alertaResponseDTO = alertaMotorService.criar(alertaRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(alertaResponseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(alertaResponseDTO);
    }

    /**
     * Listar todos os alertas
     * @param pageable Pageable que passa 10 alertas por pagina
     * @return pagina 0 de {@link Pageable} com 10 {@link AlertaResponseDTO}
     */
    @Operation(
            summary = "Listar todos os Alertas",
            description = "Retorna todos os alertas motores"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<Page<AlertaResponseDTO>> listarTodos(@PageableDefault (size = 10, page = 0 )Pageable pageable) {
        return ResponseEntity.ok().body(alertaMotorService.listarTodos(pageable));
    }

    /**
     *  Buscar todos os alertar de acordo com o motor
     * @param id id do motor
     * @param pageable Pageable que passa 10 alertas por pagina
     * @return pagina 0 de {@link Pageable} com 10 {@link AlertaResponseDTO}
     */
    @Operation(
            summary = "Buscar alertas por motor",
            description = "Pega todos os alertas que tenham o id do motor passado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerta(s) retornado(s) com sucesso"),
            @ApiResponse(responseCode = "404", description = "Motor não encontrado com id", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Page<AlertaResponseDTO>> buscarPorMotor(
            @Schema(
                    description = "Identificador do motor",
                    example = "1"
            )
            @PathVariable Integer id, @PageableDefault (size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok().body(alertaMotorService.buscarPorMotor(id,pageable));
    }


    /**
     * Atualizar status de um alerta
     * @param id identificador do alerta
     * @param alertaResolucaoDTO contem os novos dados
     * @return {@link AlertaResponseDTO} com status atualizado
     */
    @Operation(
            summary = "Atualizar Status alerta",
            description = "Atualiza o status de um alerta de acordo com o id e o valor passado no alertaResolucao"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerta atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "dados invalidos"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado com o id passado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> atualizarStatusResolucao(
            @Schema(
                    description = "Identificador unico do alerta",
                    example = "1"
            )
            @PathVariable  Integer id, @RequestBody @Valid AlertaResolucaoDTO alertaResolucaoDTO) {
        return ResponseEntity.ok().body(alertaMotorService.atualizarStatusResolucao(id, alertaResolucaoDTO));
    }
}
