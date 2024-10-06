package com.zulvani.dss.model.request;

import com.zulvani.dss.model.dto.DSSProjectDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateDSS {
    private DSSProjectDto project;
}
