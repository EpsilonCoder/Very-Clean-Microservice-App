package sn.ssi.sigmap.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ssi.sigmap.domain.Hierarchie;

/**
 * Spring Data JPA repository for the Hierarchie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HierarchieRepository extends JpaRepository<Hierarchie, Long> {}
