package com.zulvani.dss.model.request;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.DSSAlternativeParameters;
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
    private DSSAlternativeParameters dssAlternativeParameters;
}
