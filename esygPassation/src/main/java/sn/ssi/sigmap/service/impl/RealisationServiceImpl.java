package sn.ssi.sigmap.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.Realisation;
import sn.ssi.sigmap.repository.RealisationRepository;
import sn.ssi.sigmap.service.RealisationService;
import sn.ssi.sigmap.service.dto.RealisationDTO;
import sn.ssi.sigmap.service.mapper.RealisationMapper;

/**
 * Service Implementation for managing {@link Realisation}.
 */
@Service
@Transactional
public class RealisationServiceImpl implements RealisationService {

    private final Logger log = LoggerFactory.getLogger(RealisationServiceImpl.class);

    private final RealisationRepository realisationRepository;

    private final RealisationMapper realisationMapper;

    public RealisationServiceImpl(RealisationRepository realisationRepository, RealisationMapper realisationMapper) {
        this.realisationRepository = realisationRepository;
        this.realisationMapper = realisationMapper;
    }

    @Override
    public Mono<RealisationDTO> save(RealisationDTO realisationDTO) {
        log.debug("Request to save Realisation : {}", realisationDTO);
        return realisationRepository.save(realisationMapper.toEntity(realisationDTO)).map(realisationMapper::toDto);
    }

    @Override
    public Mono<RealisationDTO> update(RealisationDTO realisationDTO) {
        log.debug("Request to update Realisation : {}", realisationDTO);
        return realisationRepository.save(realisationMapper.toEntity(realisationDTO)).map(realisationMapper::toDto);
    }

    @Override
    public Mono<RealisationDTO> partialUpdate(RealisationDTO realisationDTO) {
        log.debug("Request to partially update Realisation : {}", realisationDTO);

        return realisationRepository
            .findById(realisationDTO.getId())
            .map(existingRealisation -> {
                realisationMapper.partialUpdate(existingRealisation, realisationDTO);

                return existingRealisation;
            })
            .flatMap(realisationRepository::save)
            .map(realisationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<RealisationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Realisations");
        return realisationRepository.findAllBy(pageable).map(realisationMapper::toDto);
    }

    public Mono<Long> countAll() {
        return realisationRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<RealisationDTO> findOne(Long id) {
        log.debug("Request to get Realisation : {}", id);
        return realisationRepository.findById(id).map(realisationMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Realisation : {}", id);
        return realisationRepository.deleteById(id);
    }
}
