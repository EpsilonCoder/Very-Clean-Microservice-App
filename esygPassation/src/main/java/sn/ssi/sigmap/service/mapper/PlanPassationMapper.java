package sn.ssi.sigmap.service.mapper;

import org.mapstruct.*;
import sn.ssi.sigmap.domain.PlanPassation;
import sn.ssi.sigmap.service.dto.PlanPassationDTO;

/**
 * Mapper for the entity {@link PlanPassation} and its DTO {@link PlanPassationDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlanPassationMapper extends EntityMapper<PlanPassationDTO, PlanPassation> {}
