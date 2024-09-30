package com.zulvani.dss.area.mapper;

import com.zulvani.dss.area.model.Area;
import com.zulvani.dss.area.model.AreaDto;
import com.zulvani.dss.area.model.cons.AreaType;
import org.springframework.stereotype.Component;

@Component
public class AreaMapper {

    public AreaDto map(Area area) {
        AreaDto dto = AreaDto.builder()
                .name(area.getName())
                .areaCode(area.getAreaCode())
                .build();

        if (dto.getAreaCode().length() == 2) {
            dto.setAreaType(AreaType.PROVINCE);
        }

        dto.setCreated(area.getCreated());
        dto.setLastUpdated(area.getLastUpdated());
        dto.setId(area.getId());
        dto.setDeletedAt(area.getDeletedAt());
        return dto;
    }
}
