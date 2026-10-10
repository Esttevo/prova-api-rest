package br.com.avaliacao.api_telemetria.service;

import br.com.avaliacao.api_telemetria.dto.AlertaResolucaoDTO;
import br.com.avaliacao.api_telemetria.dto.MotorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.MotorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.MotorStatusUpdateDTO;
import br.com.avaliacao.api_telemetria.entity.Motor;
import br.com.avaliacao.api_telemetria.entity.Setor;
import br.com.avaliacao.api_telemetria.mapper.MotorMapper;
import br.com.avaliacao.api_telemetria.repository.MotorRepository;
import br.com.avaliacao.api_telemetria.repository.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service de Motor, trata as regras de negocio e lança as exceções
 */
@Service
public class MotorService {
    private final MotorRepository motorRepository;
    private final SetorRepository setorRepository;
    private final MotorMapper motorMapper;

    public MotorService(MotorRepository motorRepository, SetorRepository setorRepository, MotorMapper motorMapper) {
        this.motorRepository = motorRepository;
        this.setorRepository = setorRepository;
        this.motorMapper = motorMapper;
    }

    /**
     * Criar Motor com dados passados
     * @param dto dados para criar o motor
     * @return {@link MotorResponseDTO} com id gerado pelo banco
     * @throws IllegalArgumentException caso ja exista um motor com o mesmo codigo Ativo
     */
    @Transactional
    public MotorResponseDTO criar(MotorRequestDTO dto) {
        if (motorRepository.existsByCodigoAtivo(dto.codigoAtivo())) {
            throw new IllegalArgumentException("Já existe um motor cadastrado com o código do ativo: " + dto.codigoAtivo());
        }

        Setor setor = buscarSetorSeInformado(dto.setorId());
        Motor motor = motorMapper.toEntity(dto, setor);

        return motorMapper.toDTO(motorRepository.save(motor));
    }

    /**
     * Listar todos os motores existentes
     * @param pageable criado na controller
     * @return {@link Pageable} de todos {@link MotorResponseDTO}
     */
    @Transactional(readOnly = true)
    public Page<MotorResponseDTO> listarTodos(Pageable pageable) {
        return motorRepository.findAll(pageable)
                .map(motorMapper::toDTO);
    }

    /**
     * Buscar Motor por id passado
     * @param id identificador do motor a ser buscada
     * @return {@link MotorResponseDTO} com o id passado
     * @throws EntityNotFoundException caso nao encontre o motor com o id passado
     */
    @Transactional(readOnly = true)
    public MotorResponseDTO buscarPorId(Integer id) {
        return motorRepository.findById(id)
                .map(motorMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado para o ID: " + id));
    }

    /**
     * Buscar motor por codigo Ativo
     * @param codigoAtivo codigo ativo do motor
     * @return {@link MotorResponseDTO} com o codigo Ativo passado
     * @throws EntityNotFoundException caso não ache nenhum motor com o codigo Ativo
     */
    @Transactional(readOnly = true)
    public MotorResponseDTO buscarPorCodigoAtivo(String codigoAtivo) {
        return motorRepository.findByCodigoAtivo(codigoAtivo)
                .map(motorMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado para o código: " + codigoAtivo));
    }

    /**
     * Atualizar motor completamente
     * @param id identificaodr do motor a ser atualizado
     * @param dto request com os novos dados do motor
     * @return {@link MotorResponseDTO} ja com os novos dados
     * @throws EntityNotFoundException caso não encontre o motor com o id passado
     * @throws IllegalArgumentException caso codigo ativo ja estiver em outro motor
     */
    @Transactional
    public MotorResponseDTO atualizar(Integer id, MotorRequestDTO dto) {
        Motor motor = motorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado para o ID: " + id));

        if (motorRepository.existsByCodigoAtivoAndIdNot(dto.codigoAtivo(), id)) {
            throw new IllegalArgumentException("O código de ativo informado já está em uso por outro motor.");
        }

        Setor setor = buscarSetorSeInformado(dto.setorId());

        motor.setSetor(setor);
        motor.setCodigoAtivo(dto.codigoAtivo());
        motor.setFabricante(dto.fabricante());
        motor.setModelo(dto.modelo());
        motor.setPotenciaKw(dto.potenciaKw());
        motor.setRpmNominal(dto.rpmNominal());
        motor.setDataInstalacao(dto.dataInstalacao());
        if (dto.statusAtual() != null) {
            motor.setStatusAtual(dto.statusAtual());
        }

        return motorMapper.toDTO(motorRepository.save(motor));
    }

    /**
     * Atualiza o status do motor
     * @param id do motor a ser atualizado
     * @param dto valor do novo status
     * @return {@link MotorResponseDTO} ja atualizado
     * @throws EntityNotFoundException caso não encontre o motor com o id passado pelo parametro
     */
    @Transactional
    public MotorResponseDTO atualizarStatus(Integer id, MotorStatusUpdateDTO dto) {
        Motor motor = motorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado para o ID: " + id));

        motor.setStatusAtual(dto.statusAtual());
        return motorMapper.toDTO(motorRepository.save(motor));
    }

    /**
     * Caso no request de criar exista o id de setor, valida se setor existe com o id passdo
     * @param setorId do request de criar
     * @return {@link Setor} ou null caso nenhum id for passado
     * @throws EntityNotFoundException caso possua id e ele nao corresponder a nenhum setor
     */
    private Setor buscarSetorSeInformado(Integer setorId) {
        if (setorId == null) {
            return null;
        }
        return setorRepository.findById(setorId)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado para o ID: " + setorId));
    }
}
