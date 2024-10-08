package com.zulvani.dss.model.response;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.dto.DSSParameterDto;
import com.zulvani.dss.model.dto.DSSStep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SAWResponse {
    private BigDecimal[][] matrixNormalization;
    private BigDecimal[] weights;
    private BigDecimal[] weightNormalization;
    private BigDecimal[] rank;
    private List<DSSAlternativeParameter> alternativeParameterRanked;
    private List<DSSParameterDto> parameters;
    private List<DSSStep> steps;
}
