package br.com.avaliacao.api_telemetria.mapper;

import br.com.avaliacao.api_telemetria.dto.MotorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.SetorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.SetorResponseDTO;
import br.com.avaliacao.api_telemetria.entity.AlertaMotor;
import br.com.avaliacao.api_telemetria.entity.Setor;
import org.springframework.stereotype.Component;

/**
 * Setor Mapper que mapeia setor para entidade e para dto
 */
@Component
public class SetorMapper {

    /**
     * Recebe um setorRequest e converte em entidade Setor para fazer o cadastro no banco
     * @param dto com os dados do setor
     * @return {@link Setor} com os dados do dto pronto para ser cadastrado
     */
    public Setor toEntity(SetorRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Setor.builder()
                .nome(dto.nome())
                .localizacao(dto.localizacao())
                .build();
    }

    /**
     * Recebe um setor do banco e transforma em SetorResponseDTO par exposição
     * @param entity entidade setor completa
     * @return {@link SetorResponseDTO} somente com dados não sensiveis
     */
    public SetorResponseDTO toDTO(Setor entity) {
        if (entity == null) {
            return null;
        }

        return new SetorResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getLocalizacao()
        );
}}
