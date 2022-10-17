package sn.ssi.sigmap.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ssi.sigmap.domain.AvisGeneraux;

/**
 * Spring Data JPA repository for the AvisGeneraux entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisGenerauxRepository extends JpaRepository<AvisGeneraux, Long> {}
