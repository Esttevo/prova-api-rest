package br.com.avaliacao.api_telemetria.service;

import br.com.avaliacao.api_telemetria.dto.AlertaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResolucaoDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResponseDTO;
import br.com.avaliacao.api_telemetria.entity.AlertaMotor;
import br.com.avaliacao.api_telemetria.entity.Motor;
import br.com.avaliacao.api_telemetria.mapper.AlertaMapper;
import br.com.avaliacao.api_telemetria.repository.AlertaMotorRepository;
import br.com.avaliacao.api_telemetria.repository.MotorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Alerta service, contem regras de negocio e lançamento de exceções
 */
@Service
public class AlertaMotorService {
    private final AlertaMotorRepository alertaRepository;
    private final MotorRepository motorRepository;
    private final AlertaMapper alertaMapper;

    public AlertaMotorService(AlertaMotorRepository alertaRepository, MotorRepository motorRepository, AlertaMapper alertaMapper) {
        this.alertaRepository = alertaRepository;
        this.motorRepository = motorRepository;
        this.alertaMapper = alertaMapper;
    }

    /**
     * Criar um Alerta
     * @param dto com os dados do alerta
     * @return {@link AlertaResponseDTO} ja criado
     * @throws EntityNotFoundException caso não encontre motor com o id passado
     */
    @Transactional
    public AlertaResponseDTO criar(AlertaRequestDTO dto) {
        Motor motor = motorRepository.findById(dto.motorId())
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado para o ID: " + dto.motorId()));

        AlertaMotor alerta = alertaMapper.toEntity(dto, motor);
        return alertaMapper.toDTO(alertaRepository.save(alerta));
    }

    /**
     * Listar todos os alertas
     * @param pageable criado na controller
     * @return {@link Pageable} de {@link AlertaResponseDTO}
     */
    @Transactional(readOnly = true)
    public Page<AlertaResponseDTO> listarTodos(Pageable pageable) {
        return alertaRepository.findAll(pageable)
                .map(alertaMapper::toDTO);
    }

    /**
     * Lista alertas por status de resolução
     * @param resolvido boolean que filtra os alertas buscados
     * @param pageable criado a controller
     * @return {@link Pageable} de {@link AlertaResponseDTO} que contem o resolvido igual ao passado pelo param
     */
    @Transactional(readOnly = true)
    public Page<AlertaResponseDTO> listarPorStatusResolucao(Boolean resolvido, Pageable pageable) {
        return alertaRepository.findByResolvidoOrderByDataAlertaDesc(resolvido, pageable)
                .map(alertaMapper::toDTO);
    }

    /**
     * Busca alerta Por id do motor
     * @param motorId id do motor da busca
     * @param pageable criado na controller
     * @return {@link Pageable} de {@link AlertaResponseDTO} com id do motor passado pelo parametro
     * @throws EntityNotFoundException caso nao encontre o motor com id passado
     */
    @Transactional(readOnly = true)
    public Page<AlertaResponseDTO> buscarPorMotor(Integer motorId, Pageable pageable) {
        if (!motorRepository.existsById(motorId)) {
            throw new EntityNotFoundException("Motor não encontrado para o ID: " + motorId);
        }
        return alertaRepository.findByMotorIdOrderByDataAlertaDesc(motorId, pageable)
                .map(alertaMapper::toDTO);
    }

    /**
     * Atualiza o status de resolução do alerta
     * @param id do alerta a ser atualizado
     * @param dto valor do novo status
     * @return {@link AlertaResolucaoDTO} ja atualizado
     * @throws EntityNotFoundException caso não encontre o alerta com o id passado pelo parametro
     */
    @Transactional
    public AlertaResponseDTO atualizarStatusResolucao(Integer id, AlertaResolucaoDTO dto) {
        AlertaMotor alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alerta não encontrado para o ID: " + id));

        alerta.setResolvido(dto.resolvido());
        return alertaMapper.toDTO(alertaRepository.save(alerta));
    }
}
