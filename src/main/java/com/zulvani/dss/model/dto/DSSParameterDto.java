package com.zulvani.dss.model.dto;

import com.zulvani.dss.model.cons.DSSCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DSSParameterDto {
    private Long id;
    private String code;
    private String name;
    private String labelMin;
    private String labelMax;
    private BigDecimal amount;
    private BigDecimal weight;
    private DSSCriteria criteria;
}
