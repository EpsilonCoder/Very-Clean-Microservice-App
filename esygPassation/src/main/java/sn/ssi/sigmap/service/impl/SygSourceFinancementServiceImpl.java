package sn.ssi.sigmap.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.SygSourceFinancement;
import sn.ssi.sigmap.repository.SygSourceFinancementRepository;
import sn.ssi.sigmap.service.SygSourceFinancementService;
import sn.ssi.sigmap.service.dto.SygSourceFinancementDTO;
import sn.ssi.sigmap.service.mapper.SygSourceFinancementMapper;

/**
 * Service Implementation for managing {@link SygSourceFinancement}.
 */
@Service
@Transactional
public class SygSourceFinancementServiceImpl implements SygSourceFinancementService {

    private final Logger log = LoggerFactory.getLogger(SygSourceFinancementServiceImpl.class);

    private final SygSourceFinancementRepository sygSourceFinancementRepository;

    private final SygSourceFinancementMapper sygSourceFinancementMapper;

    public SygSourceFinancementServiceImpl(
        SygSourceFinancementRepository sygSourceFinancementRepository,
        SygSourceFinancementMapper sygSourceFinancementMapper
    ) {
        this.sygSourceFinancementRepository = sygSourceFinancementRepository;
        this.sygSourceFinancementMapper = sygSourceFinancementMapper;
    }

    @Override
    public Mono<SygSourceFinancementDTO> save(SygSourceFinancementDTO sygSourceFinancementDTO) {
        log.debug("Request to save SygSourceFinancement : {}", sygSourceFinancementDTO);
        return sygSourceFinancementRepository
            .save(sygSourceFinancementMapper.toEntity(sygSourceFinancementDTO))
            .map(sygSourceFinancementMapper::toDto);
    }

    @Override
    public Mono<SygSourceFinancementDTO> update(SygSourceFinancementDTO sygSourceFinancementDTO) {
        log.debug("Request to update SygSourceFinancement : {}", sygSourceFinancementDTO);
        return sygSourceFinancementRepository
            .save(sygSourceFinancementMapper.toEntity(sygSourceFinancementDTO))
            .map(sygSourceFinancementMapper::toDto);
    }

    @Override
    public Mono<SygSourceFinancementDTO> partialUpdate(SygSourceFinancementDTO sygSourceFinancementDTO) {
        log.debug("Request to partially update SygSourceFinancement : {}", sygSourceFinancementDTO);

        return sygSourceFinancementRepository
            .findById(sygSourceFinancementDTO.getId())
            .map(existingSygSourceFinancement -> {
                sygSourceFinancementMapper.partialUpdate(existingSygSourceFinancement, sygSourceFinancementDTO);

                return existingSygSourceFinancement;
            })
            .flatMap(sygSourceFinancementRepository::save)
            .map(sygSourceFinancementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<SygSourceFinancementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SygSourceFinancements");
        return sygSourceFinancementRepository.findAllBy(pageable).map(sygSourceFinancementMapper::toDto);
    }

    public Flux<SygSourceFinancementDTO> findAllWithEagerRelationships(Pageable pageable) {
        return sygSourceFinancementRepository.findAllWithEagerRelationships(pageable).map(sygSourceFinancementMapper::toDto);
    }

    public Mono<Long> countAll() {
        return sygSourceFinancementRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<SygSourceFinancementDTO> findOne(Long id) {
        log.debug("Request to get SygSourceFinancement : {}", id);
        return sygSourceFinancementRepository.findOneWithEagerRelationships(id).map(sygSourceFinancementMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete SygSourceFinancement : {}", id);
        return sygSourceFinancementRepository.deleteById(id);
    }
}
