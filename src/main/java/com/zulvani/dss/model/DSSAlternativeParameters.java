package com.zulvani.dss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DSSAlternativeParameters {
    private List<DSSAlternativeParameter> alternativeParameters;
}
