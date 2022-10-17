package sn.ssi.sigmap.service.mapper;

import org.mapstruct.*;
import sn.ssi.sigmap.domain.SygTypeService;
import sn.ssi.sigmap.service.dto.SygTypeServiceDTO;

/**
 * Mapper for the entity {@link SygTypeService} and its DTO {@link SygTypeServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface SygTypeServiceMapper extends EntityMapper<SygTypeServiceDTO, SygTypeService> {}
