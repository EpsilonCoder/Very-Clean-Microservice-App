package sn.ssi.sigmap.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.PlanPassation;
import sn.ssi.sigmap.repository.PlanPassationRepository;
import sn.ssi.sigmap.service.PlanPassationService;
import sn.ssi.sigmap.service.dto.PlanPassationDTO;
import sn.ssi.sigmap.service.mapper.PlanPassationMapper;

/**
 * Service Implementation for managing {@link PlanPassation}.
 */
@Service
@Transactional
public class PlanPassationServiceImpl implements PlanPassationService {

    private final Logger log = LoggerFactory.getLogger(PlanPassationServiceImpl.class);

    private final PlanPassationRepository planPassationRepository;

    private final PlanPassationMapper planPassationMapper;

    public PlanPassationServiceImpl(PlanPassationRepository planPassationRepository, PlanPassationMapper planPassationMapper) {
        this.planPassationRepository = planPassationRepository;
        this.planPassationMapper = planPassationMapper;
    }

    @Override
    public Mono<PlanPassationDTO> save(PlanPassationDTO planPassationDTO) {
        log.debug("Request to save PlanPassation : {}", planPassationDTO);
        return planPassationRepository.save(planPassationMapper.toEntity(planPassationDTO)).map(planPassationMapper::toDto);
    }

    @Override
    public Mono<PlanPassationDTO> update(PlanPassationDTO planPassationDTO) {
        log.debug("Request to update PlanPassation : {}", planPassationDTO);
        return planPassationRepository.save(planPassationMapper.toEntity(planPassationDTO)).map(planPassationMapper::toDto);
    }

    @Override
    public Mono<PlanPassationDTO> partialUpdate(PlanPassationDTO planPassationDTO) {
        log.debug("Request to partially update PlanPassation : {}", planPassationDTO);

        return planPassationRepository
            .findById(planPassationDTO.getId())
            .map(existingPlanPassation -> {
                planPassationMapper.partialUpdate(existingPlanPassation, planPassationDTO);

                return existingPlanPassation;
            })
            .flatMap(planPassationRepository::save)
            .map(planPassationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<PlanPassationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanPassations");
        return planPassationRepository.findAllBy(pageable).map(planPassationMapper::toDto);
    }

    public Mono<Long> countAll() {
        return planPassationRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<PlanPassationDTO> findOne(Long id) {
        log.debug("Request to get PlanPassation : {}", id);
        return planPassationRepository.findById(id).map(planPassationMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete PlanPassation : {}", id);
        return planPassationRepository.deleteById(id);
    }
}
