package com.zulvani.dss.model.request;

import com.zulvani.dss.model.dto.DSSParameterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunDSSRequest {
    private Long projectId;
    private List<DSSParameterDto> parameters;
}
