package sn.ssi.sigmap.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.SygTypeService;
import sn.ssi.sigmap.repository.SygTypeServiceRepository;
import sn.ssi.sigmap.service.SygTypeServiceService;
import sn.ssi.sigmap.service.dto.SygTypeServiceDTO;
import sn.ssi.sigmap.service.mapper.SygTypeServiceMapper;

/**
 * Service Implementation for managing {@link SygTypeService}.
 */
@Service
@Transactional
public class SygTypeServiceServiceImpl implements SygTypeServiceService {

    private final Logger log = LoggerFactory.getLogger(SygTypeServiceServiceImpl.class);

    private final SygTypeServiceRepository sygTypeServiceRepository;

    private final SygTypeServiceMapper sygTypeServiceMapper;

    public SygTypeServiceServiceImpl(SygTypeServiceRepository sygTypeServiceRepository, SygTypeServiceMapper sygTypeServiceMapper) {
        this.sygTypeServiceRepository = sygTypeServiceRepository;
        this.sygTypeServiceMapper = sygTypeServiceMapper;
    }

    @Override
    public Mono<SygTypeServiceDTO> save(SygTypeServiceDTO sygTypeServiceDTO) {
        log.debug("Request to save SygTypeService : {}", sygTypeServiceDTO);
        return sygTypeServiceRepository.save(sygTypeServiceMapper.toEntity(sygTypeServiceDTO)).map(sygTypeServiceMapper::toDto);
    }

    @Override
    public Mono<SygTypeServiceDTO> update(SygTypeServiceDTO sygTypeServiceDTO) {
        log.debug("Request to update SygTypeService : {}", sygTypeServiceDTO);
        return sygTypeServiceRepository.save(sygTypeServiceMapper.toEntity(sygTypeServiceDTO)).map(sygTypeServiceMapper::toDto);
    }

    @Override
    public Mono<SygTypeServiceDTO> partialUpdate(SygTypeServiceDTO sygTypeServiceDTO) {
        log.debug("Request to partially update SygTypeService : {}", sygTypeServiceDTO);

        return sygTypeServiceRepository
            .findById(sygTypeServiceDTO.getId())
            .map(existingSygTypeService -> {
                sygTypeServiceMapper.partialUpdate(existingSygTypeService, sygTypeServiceDTO);

                return existingSygTypeService;
            })
            .flatMap(sygTypeServiceRepository::save)
            .map(sygTypeServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<SygTypeServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SygTypeServices");
        return sygTypeServiceRepository.findAllBy(pageable).map(sygTypeServiceMapper::toDto);
    }

    public Mono<Long> countAll() {
        return sygTypeServiceRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<SygTypeServiceDTO> findOne(Long id) {
        log.debug("Request to get SygTypeService : {}", id);
        return sygTypeServiceRepository.findById(id).map(sygTypeServiceMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete SygTypeService : {}", id);
        return sygTypeServiceRepository.deleteById(id);
    }
}
