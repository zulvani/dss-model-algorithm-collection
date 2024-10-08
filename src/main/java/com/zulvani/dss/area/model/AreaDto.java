package com.zulvani.dss.area.model;

import com.zulvani.dss.area.model.cons.AreaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaDto extends BaseDto {
    private String areaCode;
    private String name;
    private AreaType areaType;
}
