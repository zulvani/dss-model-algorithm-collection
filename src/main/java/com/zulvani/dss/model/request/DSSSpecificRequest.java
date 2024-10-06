package com.zulvani.dss.model.request;

import com.zulvani.dss.model.dto.DSSParameterDto;
import com.zulvani.dss.model.cons.DSSAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DSSSpecificRequest {
    private String name;
    private DSSAlgorithm method;
    private List<DSSParameterDto> parameters;

}