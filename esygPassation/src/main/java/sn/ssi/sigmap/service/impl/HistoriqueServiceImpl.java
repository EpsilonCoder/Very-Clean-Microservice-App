package sn.ssi.sigmap.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.ssi.sigmap.domain.Historique;
import sn.ssi.sigmap.repository.HistoriqueRepository;
import sn.ssi.sigmap.service.HistoriqueService;
import sn.ssi.sigmap.service.dto.HistoriqueDTO;
import sn.ssi.sigmap.service.mapper.HistoriqueMapper;

/**
 * Service Implementation for managing {@link Historique}.
 */
@Service
@Transactional
public class HistoriqueServiceImpl implements HistoriqueService {

    private final Logger log = LoggerFactory.getLogger(HistoriqueServiceImpl.class);

    private final HistoriqueRepository historiqueRepository;

    private final HistoriqueMapper historiqueMapper;

    public HistoriqueServiceImpl(HistoriqueRepository historiqueRepository, HistoriqueMapper historiqueMapper) {
        this.historiqueRepository = historiqueRepository;
        this.historiqueMapper = historiqueMapper;
    }

    @Override
    public Mono<HistoriqueDTO> save(HistoriqueDTO historiqueDTO) {
        log.debug("Request to save Historique : {}", historiqueDTO);
        return historiqueRepository.save(historiqueMapper.toEntity(historiqueDTO)).map(historiqueMapper::toDto);
    }

    @Override
    public Mono<HistoriqueDTO> update(HistoriqueDTO historiqueDTO) {
        log.debug("Request to update Historique : {}", historiqueDTO);
        return historiqueRepository.save(historiqueMapper.toEntity(historiqueDTO)).map(historiqueMapper::toDto);
    }

    @Override
    public Mono<HistoriqueDTO> partialUpdate(HistoriqueDTO historiqueDTO) {
        log.debug("Request to partially update Historique : {}", historiqueDTO);

        return historiqueRepository
            .findById(historiqueDTO.getId())
            .map(existingHistorique -> {
                historiqueMapper.partialUpdate(existingHistorique, historiqueDTO);

                return existingHistorique;
            })
            .flatMap(historiqueRepository::save)
            .map(historiqueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<HistoriqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Historiques");
        return historiqueRepository.findAllBy(pageable).map(historiqueMapper::toDto);
    }

    public Mono<Long> countAll() {
        return historiqueRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<HistoriqueDTO> findOne(Long id) {
        log.debug("Request to get Historique : {}", id);
        return historiqueRepository.findById(id).map(historiqueMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Historique : {}", id);
        return historiqueRepository.deleteById(id);
    }
}
