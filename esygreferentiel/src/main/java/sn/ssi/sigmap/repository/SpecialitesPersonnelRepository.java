package sn.ssi.sigmap.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ssi.sigmap.domain.SpecialitesPersonnel;

/**
 * Spring Data JPA repository for the SpecialitesPersonnel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialitesPersonnelRepository extends JpaRepository<SpecialitesPersonnel, Long> {}
