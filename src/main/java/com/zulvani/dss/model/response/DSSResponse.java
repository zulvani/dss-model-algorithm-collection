package com.zulvani.dss.model.response;

import com.zulvani.dss.model.dto.DSSParameterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DSSResponse {
    private List<DSSParameterDto> parameters;
}
