package sn.ssi.sigmap.service.mapper;

import org.mapstruct.*;
import sn.ssi.sigmap.domain.ParamDate;
import sn.ssi.sigmap.service.dto.ParamDateDTO;

/**
 * Mapper for the entity {@link ParamDate} and its DTO {@link ParamDateDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParamDateMapper extends EntityMapper<ParamDateDTO, ParamDate> {}
