package api.hastobe.csms.service.mapper;

import api.hastobe.csms.model.Rate;
import api.hastobe.csms.service.dto.RateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface RateMapper extends EntityMapper<RateDTO, Rate>{

}
