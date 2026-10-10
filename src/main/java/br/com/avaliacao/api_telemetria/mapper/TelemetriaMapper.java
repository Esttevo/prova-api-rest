package br.com.avaliacao.api_telemetria.mapper;

import br.com.avaliacao.api_telemetria.dto.SetorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaResponseDTO;
import br.com.avaliacao.api_telemetria.entity.HistoricoTelemetria;
import br.com.avaliacao.api_telemetria.entity.Motor;
import br.com.avaliacao.api_telemetria.entity.Setor;
import org.springframework.stereotype.Component;

/**
 * Telemetria Mapper que mapeia a entidade Telemetria para Entidade e Mapper
 */
@Component
public class TelemetriaMapper {
    /**
     * Recebe um telemertiaRequesst e um motor e converte em entidade HistoricoTelemetria para fazer o cadastro no banco
     * @param dto com os dados da telemetria
     * @param motor entidade motor buscado no banco
     * @return {@link HistoricoTelemetria} com os dados do dto pronto para ser cadastrado
     */
    public HistoricoTelemetria toEntity(TelemetriaRequestDTO dto, Motor motor) {
        if (dto == null) {
            return null;
        }

        return HistoricoTelemetria.builder()
                .motor(motor)
                .temperaturaCarcaca(dto.temperaturaCarcaca())
                .rpmAtual(dto.rpmAtual())
                .vibracaoGlobal(dto.vibracaoGlobal())
                .build();
    }

    /**
     * Recebe um HistoricoTelemetria do banco e transforma em TelemetriaResponseDTO para exposição
     * @param entity entidade HistoricoTelemetria completa
     * @return {@link TelemetriaResponseDTO} somente com dados não sensiveis
     */
    public TelemetriaResponseDTO toDTO(HistoricoTelemetria entity) {
        if (entity == null) {
            return null;
        }

        Integer motorId = (entity.getMotor() != null) ? entity.getMotor().getId() : null;

        return new TelemetriaResponseDTO(
                entity.getId(),
                motorId,
                entity.getDataHora(),
                entity.getTemperaturaCarcaca(),
                entity.getRpmAtual(),
                entity.getVibracaoGlobal()
        );
    }
}
