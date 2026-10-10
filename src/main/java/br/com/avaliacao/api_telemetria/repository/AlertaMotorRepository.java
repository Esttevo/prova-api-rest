package br.com.avaliacao.api_telemetria.repository;

import br.com.avaliacao.api_telemetria.entity.AlertaMotor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe que consultas no banco da tabela de Alerta Motor
 */
@Repository
public interface AlertaMotorRepository extends JpaRepository<AlertaMotor, Integer> {
    /**
     * Procura os alertas que tem o id do motor passado no parametro, retornano um pageable de todos
     * @param motorId id do motor
     * @param pageable pageable que armazena os alertas
     * @return {@link Pageable} de {@link AlertaMotor} que possuem o id do motor
     */
    Page<AlertaMotor> findByMotorIdOrderByDataAlertaDesc(Integer motorId, Pageable pageable);

    /**
     * Procura os alertas que possuem o status passado
     * @param resolvido status passado pelo parametro
     * @param pageable pageable que armazena os alertas
     * @return {@link Pageable} de {@link AlertaMotor} que possuem o status passado
     */
    Page<AlertaMotor> findByResolvidoOrderByDataAlertaDesc(Boolean resolvido, Pageable pageable);
}
