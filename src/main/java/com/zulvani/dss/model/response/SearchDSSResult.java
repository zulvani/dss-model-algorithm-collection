package com.zulvani.dss.model.response;

import com.zulvani.dss.model.dto.DSSProjectDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDSSResult {
    private List<DSSProjectDto> data;
}
