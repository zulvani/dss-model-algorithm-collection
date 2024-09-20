package com.zulvani.dss.model;

import com.zulvani.dss.model.cons.DSSCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DSSAlternativeParameter {
    private String alternativeName;
    private Map<String, BigDecimal> parameters;
    private List<BigDecimal> weights;
    private List<DSSCriteria> criteria;
}
