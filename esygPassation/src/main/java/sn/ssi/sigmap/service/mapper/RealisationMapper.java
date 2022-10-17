package sn.ssi.sigmap.service.mapper;

import org.mapstruct.*;
import sn.ssi.sigmap.domain.PlanPassation;
import sn.ssi.sigmap.domain.Realisation;
import sn.ssi.sigmap.service.dto.PlanPassationDTO;
import sn.ssi.sigmap.service.dto.RealisationDTO;

/**
 * Mapper for the entity {@link Realisation} and its DTO {@link RealisationDTO}.
 */
@Mapper(componentModel = "spring")
public interface RealisationMapper extends EntityMapper<RealisationDTO, Realisation> {
    @Mapping(target = "planPassation", source = "planPassation", qualifiedByName = "planPassationId")
    RealisationDTO toDto(Realisation s);

    @Named("planPassationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlanPassationDTO toDtoPlanPassationId(PlanPassation planPassation);
}
