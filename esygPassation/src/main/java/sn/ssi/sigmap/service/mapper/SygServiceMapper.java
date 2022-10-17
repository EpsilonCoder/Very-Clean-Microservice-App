package sn.ssi.sigmap.service.mapper;

import org.mapstruct.*;
import sn.ssi.sigmap.domain.SygService;
import sn.ssi.sigmap.domain.SygTypeService;
import sn.ssi.sigmap.service.dto.SygServiceDTO;
import sn.ssi.sigmap.service.dto.SygTypeServiceDTO;

/**
 * Mapper for the entity {@link SygService} and its DTO {@link SygServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface SygServiceMapper extends EntityMapper<SygServiceDTO, SygService> {
    @Mapping(target = "sygTypeService", source = "sygTypeService", qualifiedByName = "sygTypeServiceLibelle")
    SygServiceDTO toDto(SygService s);

    @Named("sygTypeServiceLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    SygTypeServiceDTO toDtoSygTypeServiceLibelle(SygTypeService sygTypeService);
}
