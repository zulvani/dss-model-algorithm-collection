package com.zulvani.dss.model.request;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.dto.DSSParameterDto;
import com.zulvani.dss.model.DSSWeight;
import com.zulvani.dss.model.cons.DSSAlgorithm;
import com.zulvani.dss.model.cons.DSSCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DSSRequest {
    private DSSAlgorithm method;
    private DSSCriteria[] criteria;
    private String[] parameters;
    private DSSWeight weight;
    private List<DSSAlternativeParameter> dssAlternativeParameters;
    private List<DSSParameterDto> parameterObjects;
}