package sn.ssi.sigmap.service.mapper;

import org.mapstruct.*;
import sn.ssi.sigmap.domain.SygSourceFinancement;
import sn.ssi.sigmap.domain.SygTypeSourceFinancement;
import sn.ssi.sigmap.service.dto.SygSourceFinancementDTO;
import sn.ssi.sigmap.service.dto.SygTypeSourceFinancementDTO;

/**
 * Mapper for the entity {@link SygSourceFinancement} and its DTO {@link SygSourceFinancementDTO}.
 */
@Mapper(componentModel = "spring")
public interface SygSourceFinancementMapper extends EntityMapper<SygSourceFinancementDTO, SygSourceFinancement> {
    @Mapping(target = "sygTypeSourceFinancement", source = "sygTypeSourceFinancement", qualifiedByName = "sygTypeSourceFinancementLibelle")
    SygSourceFinancementDTO toDto(SygSourceFinancement s);

    @Named("sygTypeSourceFinancementLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    SygTypeSourceFinancementDTO toDtoSygTypeSourceFinancementLibelle(SygTypeSourceFinancement sygTypeSourceFinancement);
}
