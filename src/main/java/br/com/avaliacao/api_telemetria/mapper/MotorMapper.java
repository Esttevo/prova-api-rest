package br.com.avaliacao.api_telemetria.mapper;

import br.com.avaliacao.api_telemetria.dto.MotorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.MotorResponseDTO;
import br.com.avaliacao.api_telemetria.entity.AlertaMotor;
import br.com.avaliacao.api_telemetria.entity.Motor;
import br.com.avaliacao.api_telemetria.entity.Setor;
import org.springframework.stereotype.Component;
/**
 * Motor Mapper que mapeia motor para entidade e dto
 */
@Component
public class MotorMapper {

    /**
     * Recebe um setor e um motorRequest e converte em entidade motor para fazer o cadastro no banco
     * @param dto com os dados do motor
     * @param setor setor buscado no banco para fazer relacionamento
     * @return {@link Motor} com os dados do dto pronto para ser cadastrado
     */
    public Motor toEntity(MotorRequestDTO dto, Setor setor) {
        if (dto == null) {
            return null;
        }

        return Motor.builder()
                .setor(setor)
                .codigoAtivo(dto.codigoAtivo())
                .fabricante(dto.fabricante())
                .modelo(dto.modelo())
                .potenciaKw(dto.potenciaKw())
                .rpmNominal(dto.rpmNominal())
                .dataInstalacao(dto.dataInstalacao())
                .statusAtual(dto.statusAtual() != null ? dto.statusAtual() : "Operando")
                .build();
    }

    /**
     * Recebe um motor do banco e transforma em MotorResponseDTO par exposição
     * @param entity entidade motor completa
     * @return {@link MotorResponseDTO} somente com dados não sensiveis
     */
    public MotorResponseDTO toDTO(Motor entity) {
        if (entity == null) {
            return null;
        }

        Integer setorId = entity.getSetor() != null ? entity.getSetor().getId() : null;
        String setorNome = entity.getSetor() != null ? entity.getSetor().getNome() : null;

        return new MotorResponseDTO(
                entity.getId(),
                setorId,
                setorNome,
                entity.getCodigoAtivo(),
                entity.getFabricante(),
                entity.getModelo(),
                entity.getPotenciaKw(),
                entity.getRpmNominal(),
                entity.getDataInstalacao(),
                entity.getStatusAtual()
        );
    }
}
