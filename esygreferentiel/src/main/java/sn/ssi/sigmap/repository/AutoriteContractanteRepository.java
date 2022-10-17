package sn.ssi.sigmap.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ssi.sigmap.domain.AutoriteContractante;

/**
 * Spring Data JPA repository for the AutoriteContractante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutoriteContractanteRepository extends JpaRepository<AutoriteContractante, Long> {}
