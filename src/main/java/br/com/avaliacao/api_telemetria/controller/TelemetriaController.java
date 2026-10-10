package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.AlertaResponseDTO;
import br.com.avaliacao.api_telemetria.dto.SetorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaResponseDTO;
import br.com.avaliacao.api_telemetria.service.TelemetriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import jdk.jfr.Threshold;
import org.springdoc.core.annotations.ParameterObject;
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
 * Telemetria controller com todos os enpoints de telemetria
 */


@Tag(name = "telemetria Controller", description = "Classe que possui os enpooints de telemetria")
@RequestMapping("api/v1/telemetrias")
@RestController
public class TelemetriaController {

    private final TelemetriaService telemetriaService;

    public TelemetriaController(TelemetriaService telemetriaService) {
        this.telemetriaService = telemetriaService;
    }

    /**
     * Listar todos as telemetrias
     * @param pageable criado automaticamente
     * @return {@link Pageable} de todos {@link TelemetriaResponseDTO}
     */
    @Operation(summary = "Listar as telemetrias", description = "listar todos as telemetrias existentes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Telemetrias listados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<TelemetriaResponseDTO>> listarTodos(@ParameterObject @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok().body(telemetriaService.listarTodos(pageable));
    }

    /**
     * Buscar telemetria por id
     * @param id da telemetria a ser buscado
     * @return {@link TelemetriaResponseDTO} com o id passado no parametro
     */
    @Operation(summary = "Buscar telemetria por id", description = "Busca a telemetria pelo id passado no parametro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "telemetria encontrada e retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "telemetria com id passado não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TelemetriaResponseDTO> buscarPorId(
            @Schema(description = "identificador da telemetria", example = "1")
            @PathVariable Long id){
        return ResponseEntity.ok().body(telemetriaService.buscarPorId(id));
    }

    /**
     *  Buscar todas as telemetrias de acordo com o motor
     * @param id id do motor
     * @param pageable Pageable que passa 10 alertas por pagina
     * @return pagina 0 de {@link Pageable} com 10 {@link TelemetriaResponseDTO}
     */
    @Operation(
            summary = "Buscar telemetrias por motor",
            description = "Pega todas as telemetrias que tenham o id do motor passado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "telemetria(s) telemetria(s) com sucesso"),
            @ApiResponse(responseCode = "404", description = "Motor não encontrado com id", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping("/motor/{id}")
    public ResponseEntity<Page<TelemetriaResponseDTO>> listarPorMotor(
            @Schema(description = "identificador do motor", example = "1")
            @PathVariable Integer id, @ParameterObject @PageableDefault (size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.ok().body(telemetriaService.buscarPorMotor(id, pageable));
    }

    /**
     * Registrar Telemetria
     * @param telemetriaRequestDTO dados da telemetria a ser cadastrada
     * @return {@link TelemetriaResponseDTO} criada com o id gerado pelo banco
     */
    @Operation(summary = "Registrar telemetria", description = "Registrar telemetria de acordo com os dados passados pelo request")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "telemetria criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Motor não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<TelemetriaResponseDTO> registrar(@Valid @RequestBody TelemetriaRequestDTO telemetriaRequestDTO)
    {
       TelemetriaResponseDTO telemetriaResponseDTO = telemetriaService.registrar(telemetriaRequestDTO);

       URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(telemetriaResponseDTO.id()).toUri();

       return ResponseEntity.created(uri).body(telemetriaResponseDTO);

    }

}
