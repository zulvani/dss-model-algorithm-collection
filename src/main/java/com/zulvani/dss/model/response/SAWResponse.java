package com.zulvani.dss.model.response;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.DSSParameter;
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
    private List<DSSParameter> parameters;
}
