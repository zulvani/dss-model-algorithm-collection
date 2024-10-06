package com.zulvani.dss.model.request;

import com.zulvani.dss.model.dto.DSSAlternativeDto;
import com.zulvani.dss.model.dto.DSSProjectDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateDSS {
    private DSSProjectDto project;
    private List<DSSAlternativeDto> alternatives;
}
