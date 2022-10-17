package sn.ssi.sigmap.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ssi.sigmap.domain.NaturesGarantie;

/**
 * Spring Data JPA repository for the NaturesGarantie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaturesGarantieRepository extends JpaRepository<NaturesGarantie, Long> {}
