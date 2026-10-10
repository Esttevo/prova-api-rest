package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.MotorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.SetorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.SetorResponseDTO;
import br.com.avaliacao.api_telemetria.service.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
 * Setor controller com todos os enpoits de setor
 */
@Tag(name = "Setor Controller", description = "Classe que possui os enpooints enpoints de setor")
@RequestMapping("api/v1/setores")
@RestController
public class SetorController {

    private final SetorService service;

    public SetorController(SetorService service) {
        this.service = service;
    }

    /**
     * Criar Setor
     * @param setorRequestDTO dados do setor a ser criado
     * @return {@link SetorResponseDTO} criado com id gerado pelo banco
     */
    @Operation(summary = "Criar Setor", description = "Criar setor de acordo com os dados passados pelo request")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Setor criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ja existe setor com esse nome", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SetorResponseDTO> criar(@RequestBody @Valid SetorRequestDTO setorRequestDTO) {
        SetorResponseDTO setorResponseDTO = service.criar(setorRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(setorResponseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(setorResponseDTO);
    }

    /**
     * Listar todos os Setores
     * @param pageable criado automaticamente
     * @return {@link Pageable} de todos {@link SetorResponseDTO}
     */
    @Operation(summary = "Listar Setores", description = "listar todos os setores existentes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Setores listados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<SetorResponseDTO>> listarTodos(@ParameterObject @PageableDefault (size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok().body(service.listarTodos(pageable));
    }

    /**
     * Buscar setor por id
     * @param id do setor a ser buscado
     * @return {@link SetorResponseDTO} com o id passado no parametro
     */
    @Operation(summary = "Buscar setor por id", description = "Busca o setor pelo id passado no parametro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "setor encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "setor com id passado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SetorResponseDTO> buscarPorId(
            @Schema(description = "identificador do setor", example = "1")
            @PathVariable Integer id){

        return ResponseEntity.ok().body(service.buscarPorId(id));
    }

    /**
     * Atualizar Setor completamente
     * @param id identificador do Setor a ser atualizado
     * @param  setorRequestDTO com os novos dados do Setor
     * @return {@link SetorResponseDTO} ja atualizado
     */

    @Operation(summary = "Atualizar Setor", description = "Atualiza o Setor por completo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Setor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados novos do Setor inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Setor com id passado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SetorResponseDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid SetorRequestDTO setorRequestDTO){
        return ResponseEntity.ok().body(service.atualizar(id, setorRequestDTO));
    }
}
