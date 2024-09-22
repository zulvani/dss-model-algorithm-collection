package com.zulvani.dss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DSSAlternativeParameter {
    private String alternativeName;
    private BigDecimal[] parameterValues;
}