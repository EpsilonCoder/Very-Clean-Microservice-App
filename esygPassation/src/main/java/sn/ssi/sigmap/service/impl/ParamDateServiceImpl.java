package sn.ssi.sigmap.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.ParamDate;
import sn.ssi.sigmap.repository.ParamDateRepository;
import sn.ssi.sigmap.service.ParamDateService;
import sn.ssi.sigmap.service.dto.ParamDateDTO;
import sn.ssi.sigmap.service.mapper.ParamDateMapper;

/**
 * Service Implementation for managing {@link ParamDate}.
 */
@Service
@Transactional
public class ParamDateServiceImpl implements ParamDateService {

    private final Logger log = LoggerFactory.getLogger(ParamDateServiceImpl.class);

    private final ParamDateRepository paramDateRepository;

    private final ParamDateMapper paramDateMapper;

    public ParamDateServiceImpl(ParamDateRepository paramDateRepository, ParamDateMapper paramDateMapper) {
        this.paramDateRepository = paramDateRepository;
        this.paramDateMapper = paramDateMapper;
    }

    @Override
    public Mono<ParamDateDTO> save(ParamDateDTO paramDateDTO) {
        log.debug("Request to save ParamDate : {}", paramDateDTO);
        return paramDateRepository.save(paramDateMapper.toEntity(paramDateDTO)).map(paramDateMapper::toDto);
    }

    @Override
    public Mono<ParamDateDTO> update(ParamDateDTO paramDateDTO) {
        log.debug("Request to update ParamDate : {}", paramDateDTO);
        return paramDateRepository.save(paramDateMapper.toEntity(paramDateDTO)).map(paramDateMapper::toDto);
    }

    @Override
    public Mono<ParamDateDTO> partialUpdate(ParamDateDTO paramDateDTO) {
        log.debug("Request to partially update ParamDate : {}", paramDateDTO);

        return paramDateRepository
            .findById(paramDateDTO.getId())
            .map(existingParamDate -> {
                paramDateMapper.partialUpdate(existingParamDate, paramDateDTO);

                return existingParamDate;
            })
            .flatMap(paramDateRepository::save)
            .map(paramDateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ParamDateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParamDates");
        return paramDateRepository.findAllBy(pageable).map(paramDateMapper::toDto);
    }

    public Mono<Long> countAll() {
        return paramDateRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ParamDateDTO> findOne(Long id) {
        log.debug("Request to get ParamDate : {}", id);
        return paramDateRepository.findById(id).map(paramDateMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete ParamDate : {}", id);
        return paramDateRepository.deleteById(id);
    }
}
