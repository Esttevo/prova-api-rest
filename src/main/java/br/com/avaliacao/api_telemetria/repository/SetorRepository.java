package br.com.avaliacao.api_telemetria.repository;

import br.com.avaliacao.api_telemetria.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe que consultas no banco da tabela de Setor
 */
@Repository
public interface SetorRepository extends JpaRepository<Setor, Integer> {
    /**
     * Procura se existe um setor com o nome passado pelo param
     * @param nome nome a ser buscado
     * @return {@link Boolean} (não wrapper) true se existe e false se não existe
     */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Procura se existe um setor com o nome e id passado pelos params
     * @param nome nome a ser buscado
     * @param id id a ser buscado
     * @return {@link Boolean} (não wrapper) true se existe e false se não existe
     */
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Integer id);
}
