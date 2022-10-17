package sn.ssi.sigmap.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.SygService;
import sn.ssi.sigmap.repository.SygServiceRepository;
import sn.ssi.sigmap.service.SygServiceService;
import sn.ssi.sigmap.service.dto.SygServiceDTO;
import sn.ssi.sigmap.service.mapper.SygServiceMapper;

/**
 * Service Implementation for managing {@link SygService}.
 */
@Service
@Transactional
public class SygServiceServiceImpl implements SygServiceService {

    private final Logger log = LoggerFactory.getLogger(SygServiceServiceImpl.class);

    private final SygServiceRepository sygServiceRepository;

    private final SygServiceMapper sygServiceMapper;

    public SygServiceServiceImpl(SygServiceRepository sygServiceRepository, SygServiceMapper sygServiceMapper) {
        this.sygServiceRepository = sygServiceRepository;
        this.sygServiceMapper = sygServiceMapper;
    }

    @Override
    public Mono<SygServiceDTO> save(SygServiceDTO sygServiceDTO) {
        log.debug("Request to save SygService : {}", sygServiceDTO);
        return sygServiceRepository.save(sygServiceMapper.toEntity(sygServiceDTO)).map(sygServiceMapper::toDto);
    }

    @Override
    public Mono<SygServiceDTO> update(SygServiceDTO sygServiceDTO) {
        log.debug("Request to update SygService : {}", sygServiceDTO);
        return sygServiceRepository.save(sygServiceMapper.toEntity(sygServiceDTO)).map(sygServiceMapper::toDto);
    }

    @Override
    public Mono<SygServiceDTO> partialUpdate(SygServiceDTO sygServiceDTO) {
        log.debug("Request to partially update SygService : {}", sygServiceDTO);

        return sygServiceRepository
            .findById(sygServiceDTO.getId())
            .map(existingSygService -> {
                sygServiceMapper.partialUpdate(existingSygService, sygServiceDTO);

                return existingSygService;
            })
            .flatMap(sygServiceRepository::save)
            .map(sygServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<SygServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SygServices");
        return sygServiceRepository.findAllBy(pageable).map(sygServiceMapper::toDto);
    }

    public Flux<SygServiceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return sygServiceRepository.findAllWithEagerRelationships(pageable).map(sygServiceMapper::toDto);
    }

    public Mono<Long> countAll() {
        return sygServiceRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<SygServiceDTO> findOne(Long id) {
        log.debug("Request to get SygService : {}", id);
        return sygServiceRepository.findOneWithEagerRelationships(id).map(sygServiceMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete SygService : {}", id);
        return sygServiceRepository.deleteById(id);
    }
}
