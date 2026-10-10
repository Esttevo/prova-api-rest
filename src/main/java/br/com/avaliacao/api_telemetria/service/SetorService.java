package br.com.avaliacao.api_telemetria.service;

import br.com.avaliacao.api_telemetria.dto.MotorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.SetorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.SetorResponseDTO;
import br.com.avaliacao.api_telemetria.entity.Setor;
import br.com.avaliacao.api_telemetria.mapper.SetorMapper;
import br.com.avaliacao.api_telemetria.repository.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Setor service que trata regras de negocio e faz o lançamento de exceções do que cabe ao setor
 */
@Service
public class SetorService {
    private final SetorRepository setorRepository;
    private final SetorMapper setorMapper;

    public SetorService(SetorRepository setorRepository, SetorMapper setorMapper) {
        this.setorRepository = setorRepository;
        this.setorMapper = setorMapper;
    }

    /**
     * Criar setor
     * @param dto dados passados para criar criar setor
     * @return {@link SetorResponseDTO} criado com id do banco
     * @throws IllegalArgumentException caso ja exista setor com o mesmo nome
     */
    @Transactional
    public SetorResponseDTO criar(SetorRequestDTO dto) {
        if (setorRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new IllegalArgumentException("Já existe um setor cadastrado com o nome: " + dto.nome());
        }

        Setor setor = setorMapper.toEntity(dto);
        return setorMapper.toDTO(setorRepository.save(setor));
    }

    /**
     * Listar todos os Setores
     * @param pageable criado na controller
     * @return {@link Pageable} de todos os {@link SetorResponseDTO}
     */
    @Transactional(readOnly = true)
    public Page<SetorResponseDTO> listarTodos(Pageable pageable) {
        return setorRepository.findAll(pageable)
                .map(setorMapper::toDTO);
    }
    /**
     * Buscar Setor por id passado
     * @param id identificador do Setor a ser buscada
     * @return {@link SetorResponseDTO} com o id passado
     * @throws EntityNotFoundException caso nao encontre o Setor com o id passado
     */
    @Transactional(readOnly = true)
    public SetorResponseDTO buscarPorId(Integer id) {
        return setorRepository.findById(id)
                .map(setorMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado para o ID: " + id));
    }

    /**
     * Atualizar setor completamente
     * @param id identificaodr do setor a ser atualizado
     * @param dto request com os novos dados do setor
     * @return {@link SetorResponseDTO} ja com os novos dados
     * @throws EntityNotFoundException caso não encontre o setor com o id passado
     * @throws IllegalArgumentException caso nome ja estiver em outro setor
     */
    @Transactional
    public SetorResponseDTO atualizar(Integer id, SetorRequestDTO dto) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado para o ID: " + id));

        if (setorRepository.existsByNomeIgnoreCaseAndIdNot(dto.nome(), id)) {
            throw new IllegalArgumentException("Já existe outro setor cadastrado com o nome: " + dto.nome());
        }

        setor.setNome(dto.nome());
        setor.setLocalizacao(dto.localizacao());

        return setorMapper.toDTO(setorRepository.save(setor));
    }
}
