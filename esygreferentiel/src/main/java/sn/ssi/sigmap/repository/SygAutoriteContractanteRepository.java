package sn.ssi.sigmap.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ssi.sigmap.domain.SygAutoriteContractante;

/**
 * Spring Data JPA repository for the SygAutoriteContractante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SygAutoriteContractanteRepository extends JpaRepository<SygAutoriteContractante, Long> {}
