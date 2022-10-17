package sn.ssi.sigmap.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.SygTypeSourceFinancement;
import sn.ssi.sigmap.repository.SygTypeSourceFinancementRepository;
import sn.ssi.sigmap.service.SygTypeSourceFinancementService;
import sn.ssi.sigmap.service.dto.SygTypeSourceFinancementDTO;
import sn.ssi.sigmap.service.mapper.SygTypeSourceFinancementMapper;

/**
 * Service Implementation for managing {@link SygTypeSourceFinancement}.
 */
@Service
@Transactional
public class SygTypeSourceFinancementServiceImpl implements SygTypeSourceFinancementService {

    private final Logger log = LoggerFactory.getLogger(SygTypeSourceFinancementServiceImpl.class);

    private final SygTypeSourceFinancementRepository sygTypeSourceFinancementRepository;

    private final SygTypeSourceFinancementMapper sygTypeSourceFinancementMapper;

    public SygTypeSourceFinancementServiceImpl(
        SygTypeSourceFinancementRepository sygTypeSourceFinancementRepository,
        SygTypeSourceFinancementMapper sygTypeSourceFinancementMapper
    ) {
        this.sygTypeSourceFinancementRepository = sygTypeSourceFinancementRepository;
        this.sygTypeSourceFinancementMapper = sygTypeSourceFinancementMapper;
    }

    @Override
    public Mono<SygTypeSourceFinancementDTO> save(SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO) {
        log.debug("Request to save SygTypeSourceFinancement : {}", sygTypeSourceFinancementDTO);
        return sygTypeSourceFinancementRepository
            .save(sygTypeSourceFinancementMapper.toEntity(sygTypeSourceFinancementDTO))
            .map(sygTypeSourceFinancementMapper::toDto);
    }

    @Override
    public Mono<SygTypeSourceFinancementDTO> update(SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO) {
        log.debug("Request to update SygTypeSourceFinancement : {}", sygTypeSourceFinancementDTO);
        return sygTypeSourceFinancementRepository
            .save(sygTypeSourceFinancementMapper.toEntity(sygTypeSourceFinancementDTO))
            .map(sygTypeSourceFinancementMapper::toDto);
    }

    @Override
    public Mono<SygTypeSourceFinancementDTO> partialUpdate(SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO) {
        log.debug("Request to partially update SygTypeSourceFinancement : {}", sygTypeSourceFinancementDTO);

        return sygTypeSourceFinancementRepository
            .findById(sygTypeSourceFinancementDTO.getId())
            .map(existingSygTypeSourceFinancement -> {
                sygTypeSourceFinancementMapper.partialUpdate(existingSygTypeSourceFinancement, sygTypeSourceFinancementDTO);

                return existingSygTypeSourceFinancement;
            })
            .flatMap(sygTypeSourceFinancementRepository::save)
            .map(sygTypeSourceFinancementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<SygTypeSourceFinancementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SygTypeSourceFinancements");
        return sygTypeSourceFinancementRepository.findAllBy(pageable).map(sygTypeSourceFinancementMapper::toDto);
    }

    public Mono<Long> countAll() {
        return sygTypeSourceFinancementRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<SygTypeSourceFinancementDTO> findOne(Long id) {
        log.debug("Request to get SygTypeSourceFinancement : {}", id);
        return sygTypeSourceFinancementRepository.findById(id).map(sygTypeSourceFinancementMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete SygTypeSourceFinancement : {}", id);
        return sygTypeSourceFinancementRepository.deleteById(id);
    }
}
