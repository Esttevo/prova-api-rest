package br.com.avaliacao.api_telemetria.mapper;

import br.com.avaliacao.api_telemetria.dto.AlertaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResponseDTO;
import br.com.avaliacao.api_telemetria.entity.AlertaMotor;
import br.com.avaliacao.api_telemetria.entity.Motor;
import org.springframework.stereotype.Component;

/**
 * Alerta Mapper que mapeia alerta para entidade e dto
 */
@Component
public class AlertaMapper {

    /**
     * Recebe um AlertaRequest e um motor converte em entidade AlertaMotor para fazer o cadastro no banco
     * @param dto com os dados do alerta
     * @param motor motor buscado no banco para fazer o relacionamento
     * @return {@link AlertaMotor} com os dados do dto pronto para ser cadastrado
     */
    public AlertaMotor toEntity(AlertaRequestDTO dto, Motor motor) {
        if (dto == null) {
            return null;
        }

        return AlertaMotor.builder()
                .motor(motor)
                .tipoAnomalia(dto.tipoAnomalia())
                .criticidade(dto.criticidade())
                .descricao(dto.descricao())
                .build();
    }

    /**
     * Converte a entidade AlertaMotor para AlertaMotorResponse
     * @param entity Alerta Motor com todos os dados
     * @return {@link AlertaResponseDTO} somente com os dados não sensíveis
     */
    public AlertaResponseDTO toDTO(AlertaMotor entity) {
        if (entity == null) {
            return null;
        }

        Integer motorId = entity.getMotor() != null ? entity.getMotor().getId() : null;
        String codigoAtivo = entity.getMotor() != null ? entity.getMotor().getCodigoAtivo() : null;

        return new AlertaResponseDTO(
                entity.getId(),
                motorId,
                codigoAtivo,
                entity.getDataAlerta(),
                entity.getTipoAnomalia(),
                entity.getCriticidade(),
                entity.getDescricao(),
                entity.getResolvido()
        );
    }
}
