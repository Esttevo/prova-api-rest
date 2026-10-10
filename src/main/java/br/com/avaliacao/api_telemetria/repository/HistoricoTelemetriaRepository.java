package br.com.avaliacao.api_telemetria.repository;

import br.com.avaliacao.api_telemetria.entity.HistoricoTelemetria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe que consultas no banco da tabela de Historico Telemetria
 */
@Repository
public interface HistoricoTelemetriaRepository extends JpaRepository<HistoricoTelemetria, Long> {
    /**
     * Procura todos os Historicos telemetria que possuem o motor com o id passado pelo parametro
     * @param motorId id do motor
     * @param pageable pageable que armazena os Historicos telemetrias
     * @return {@link Pageable} de {@link HistoricoTelemetria} com o id do motor passado pelo param
     */
    Page<HistoricoTelemetria> findByMotorIdOrderByDataHoraDesc(Integer motorId, Pageable pageable);
}