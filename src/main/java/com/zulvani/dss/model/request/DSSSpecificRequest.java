package com.zulvani.dss.model.request;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.DSSParameter;
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
public class DSSSpecificRequest {
    private String name;
    private DSSAlgorithm method;
    private List<DSSParameter> parameters;

}