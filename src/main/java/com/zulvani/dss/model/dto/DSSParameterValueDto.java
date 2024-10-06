package com.zulvani.dss.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DSSParameterValueDto {
    private String parameterCode;
    private BigDecimal parameterValue;
}
