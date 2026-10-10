package br.com.avaliacao.api_telemetria.repository;

import br.com.avaliacao.api_telemetria.entity.Motor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Classe que consultas no banco da tabela de Motor
 */
@Repository
public interface MotorRepository extends JpaRepository<Motor, Integer>{
    /**
     * procura um motor com o codigo ativo
     * @param codigoAtivo codigo ativo do motor
     * @return {@link Optional} de {@link Motor}
     */
    Optional<Motor> findByCodigoAtivo(String codigoAtivo);

    /**
     * procura se existe motor com codigoAtivo e retorna um boolean
     * @param codigoAtivo codigo ativo do motor
     * @return {@link Boolean}(não wrapper) true se existe e false se não existe
     */
    boolean existsByCodigoAtivo(String codigoAtivo);

    /**
     * procura se existe motor com codigoAtivo e id,  retorna um boolean
     * @param codigoAtivo codigo ativo do motor
     * @param id id do motor
     * @return {@link Boolean}(não wrapper) true se existe e false se não existe
     */
    boolean existsByCodigoAtivoAndIdNot(String codigoAtivo, Integer id);
}
