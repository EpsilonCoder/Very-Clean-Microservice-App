package sn.ssi.sigmap.service.mapper;

import org.mapstruct.*;
import sn.ssi.sigmap.domain.Historique;
import sn.ssi.sigmap.domain.PlanPassation;
import sn.ssi.sigmap.service.dto.HistoriqueDTO;
import sn.ssi.sigmap.service.dto.PlanPassationDTO;

/**
 * Mapper for the entity {@link Historique} and its DTO {@link HistoriqueDTO}.
 */
@Mapper(componentModel = "spring")
public interface HistoriqueMapper extends EntityMapper<HistoriqueDTO, Historique> {
    @Mapping(target = "planPassation", source = "planPassation", qualifiedByName = "planPassationId")
    HistoriqueDTO toDto(Historique s);

    @Named("planPassationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlanPassationDTO toDtoPlanPassationId(PlanPassation planPassation);
}
