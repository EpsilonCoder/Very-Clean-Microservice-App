package sn.ssi.sigmap.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.SygTypeMarche;
import sn.ssi.sigmap.repository.SygTypeMarcheRepository;
import sn.ssi.sigmap.service.SygTypeMarcheService;
import sn.ssi.sigmap.service.dto.SygTypeMarcheDTO;
import sn.ssi.sigmap.service.mapper.SygTypeMarcheMapper;

/**
 * Service Implementation for managing {@link SygTypeMarche}.
 */
@Service
@Transactional
public class SygTypeMarcheServiceImpl implements SygTypeMarcheService {

    private final Logger log = LoggerFactory.getLogger(SygTypeMarcheServiceImpl.class);

    private final SygTypeMarcheRepository sygTypeMarcheRepository;

    private final SygTypeMarcheMapper sygTypeMarcheMapper;

    public SygTypeMarcheServiceImpl(SygTypeMarcheRepository sygTypeMarcheRepository, SygTypeMarcheMapper sygTypeMarcheMapper) {
        this.sygTypeMarcheRepository = sygTypeMarcheRepository;
        this.sygTypeMarcheMapper = sygTypeMarcheMapper;
    }

    @Override
    public Mono<SygTypeMarcheDTO> save(SygTypeMarcheDTO sygTypeMarcheDTO) {
        log.debug("Request to save SygTypeMarche : {}", sygTypeMarcheDTO);
        return sygTypeMarcheRepository.save(sygTypeMarcheMapper.toEntity(sygTypeMarcheDTO)).map(sygTypeMarcheMapper::toDto);
    }

    @Override
    public Mono<SygTypeMarcheDTO> update(SygTypeMarcheDTO sygTypeMarcheDTO) {
        log.debug("Request to update SygTypeMarche : {}", sygTypeMarcheDTO);
        return sygTypeMarcheRepository.save(sygTypeMarcheMapper.toEntity(sygTypeMarcheDTO)).map(sygTypeMarcheMapper::toDto);
    }

    @Override
    public Mono<SygTypeMarcheDTO> partialUpdate(SygTypeMarcheDTO sygTypeMarcheDTO) {
        log.debug("Request to partially update SygTypeMarche : {}", sygTypeMarcheDTO);

        return sygTypeMarcheRepository
            .findById(sygTypeMarcheDTO.getId())
            .map(existingSygTypeMarche -> {
                sygTypeMarcheMapper.partialUpdate(existingSygTypeMarche, sygTypeMarcheDTO);

                return existingSygTypeMarche;
            })
            .flatMap(sygTypeMarcheRepository::save)
            .map(sygTypeMarcheMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<SygTypeMarcheDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SygTypeMarches");
        return sygTypeMarcheRepository.findAllBy(pageable).map(sygTypeMarcheMapper::toDto);
    }

    public Mono<Long> countAll() {
        return sygTypeMarcheRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<SygTypeMarcheDTO> findOne(Long id) {
        log.debug("Request to get SygTypeMarche : {}", id);
        return sygTypeMarcheRepository.findById(id).map(sygTypeMarcheMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete SygTypeMarche : {}", id);
        return sygTypeMarcheRepository.deleteById(id);
    }
}
