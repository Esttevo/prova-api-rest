package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.MotorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.MotorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.MotorStatusUpdateDTO;
import br.com.avaliacao.api_telemetria.service.MotorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jdk.jfr.Threshold;
import org.apache.coyote.Response;
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
 * Controller de Motores, que possui todos os endpoints de motores
 */
@Tag(name = "Controller de motores", description ="Classe responsavel pelos endpoins de motor" )
@RestController
@RequestMapping("/api/v1/motores")
public class MotorController {

    private final MotorService motorService;

    public MotorController(MotorService motorService) {
        this.motorService = motorService;
    }

    /**
     * Criar motor
     * @param motorRequestDTO dados para a criação do motor
     * @return {@link MotorResponseDTO} com o id gerado pelo banco
     */

    @Operation(
            summary = "Criar motor",
            description = "Cria motor a partir dos dados passados pelo Request"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Motor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Caso ja possua motor com codigo ativo", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MotorResponseDTO> criar( @RequestBody MotorRequestDTO motorRequestDTO) {
        MotorResponseDTO motorResponseDTO =motorService.criar(motorRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(motorResponseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(motorResponseDTO);
    }


    /**
     * Listar todos os Motores
     * @param pageable passado automaticamente
     * @return {@link Pageable} de {@link MotorResponseDTO}
     */

    @Operation(
            summary = "listar Motores",
            description = "Lista todos os motores com um pageable de 10 por pagina"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<MotorResponseDTO>> listarTodos(@ParameterObject @PageableDefault (size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok().body(motorService.listarTodos(pageable));
    }

    /**
     * Buscar motor por id
     * @param id do motor a ser buscado
     * @return {@link MotorResponseDTO} com o id passado no parametro
     */
    @Operation(summary = "Buscar motor por id", description = "Busca o motor pelo id passado no parametro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Motor encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "motor com id passado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MotorResponseDTO> buscarPorId(
            @Schema(description = "Identificador do motor",example = "1")
            @PathVariable Integer id) {
            return ResponseEntity.ok().body(motorService.buscarPorId(id));
    }

    /**
     * Buscar Motor por codigoAtivo
     * @param codigoAtivo codigo do motor a ser buscado
     * @return {@link MotorResponseDTO} com o codigo passado
     */

    @Operation(summary = "buscar motor por codigo ativo", description = "Procura o motor no banco que contem o codigoAtivo passado no parametro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Motor encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "motor com codigoAtivo passado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping("/filtro")
    public ResponseEntity<MotorResponseDTO> buscarPorCodigoAtivo(
            @Schema(description = "Codigo ativo do motor a ser buscado", example = "MOT-USI-001")
            @RequestParam String codigoAtivo) {
        return ResponseEntity.ok().body(motorService.buscarPorCodigoAtivo(codigoAtivo));
    }


    /**
     * Atualizar Motor completamente
     * @param id identificador do motor a ser atualizado
     * @param motorRequestDTO request com os novos dados do motor
     * @return {@link MotorResponseDTO} ja atualizado
     */

    @Operation(summary = "Atualizar motor", description = "Atualiza o motor por completo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Motor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados novos do motor inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "motor com id passado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<MotorResponseDTO> atualizar(
            @Schema(description = "identificador do motor", example = "1")
            @PathVariable  Integer id,@Valid @RequestBody MotorRequestDTO motorRequestDTO){

        return ResponseEntity.ok().body(motorService.atualizar(id, motorRequestDTO));
    }

    /**
     * Atualizar status do motor
     * @param id id do motor a ser atualizado
     * @param motorStatusUpdateDTO request com o novo status do motor
     * @return {@link MotorResponseDTO} com status atualizado
     */
    @Operation(summary = "Atualizar motor", description = "Atualiza o status do motor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Motor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados novos do motor inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "motor com id passado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<MotorResponseDTO> atualizarStatus(
            @Schema(description = "Identificador do motor", example = "1")
            @PathVariable Integer id, @Valid @RequestBody MotorStatusUpdateDTO motorStatusUpdateDTO) {
        return ResponseEntity.ok().body(motorService.atualizarStatus(id, motorStatusUpdateDTO));
    }
}
