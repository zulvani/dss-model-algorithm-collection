package com.zulvani.dss.model;

import com.zulvani.dss.model.cons.DSSWeightMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DSSWeight {
    private DSSWeightMethod method;
    private BigDecimal[] values;
}
