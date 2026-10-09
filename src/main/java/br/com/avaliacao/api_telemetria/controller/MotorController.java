package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.MotorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.MotorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.MotorStatusUpdateDTO;
import br.com.avaliacao.api_telemetria.service.MotorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
            @ApiResponse(responseCode = "400", description = "Dados invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<MotorResponseDTO> criar(@Valid @RequestBody MotorRequestDTO motorRequestDTO) {
        MotorResponseDTO motorResponseDTO =motorService.criar(motorRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(motorResponseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(motorResponseDTO);
    }


    public void listarTodos(){

    }


    public void buscarPorId() {

    }


    public void buscarPorCodigoAtivo() {
    }


    public  void atualizar(){

    }


    public void atualizarStatus(){

    }
}
