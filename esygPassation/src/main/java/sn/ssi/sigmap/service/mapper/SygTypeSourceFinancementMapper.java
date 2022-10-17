package sn.ssi.sigmap.service.mapper;

import org.mapstruct.*;
import sn.ssi.sigmap.domain.SygTypeSourceFinancement;
import sn.ssi.sigmap.service.dto.SygTypeSourceFinancementDTO;

/**
 * Mapper for the entity {@link SygTypeSourceFinancement} and its DTO {@link SygTypeSourceFinancementDTO}.
 */
@Mapper(componentModel = "spring")
public interface SygTypeSourceFinancementMapper extends EntityMapper<SygTypeSourceFinancementDTO, SygTypeSourceFinancement> {}
