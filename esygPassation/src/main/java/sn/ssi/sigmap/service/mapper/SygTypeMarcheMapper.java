package sn.ssi.sigmap.service.mapper;

import org.mapstruct.*;
import sn.ssi.sigmap.domain.SygTypeMarche;
import sn.ssi.sigmap.service.dto.SygTypeMarcheDTO;

/**
 * Mapper for the entity {@link SygTypeMarche} and its DTO {@link SygTypeMarcheDTO}.
 */
@Mapper(componentModel = "spring")
public interface SygTypeMarcheMapper extends EntityMapper<SygTypeMarcheDTO, SygTypeMarche> {}
