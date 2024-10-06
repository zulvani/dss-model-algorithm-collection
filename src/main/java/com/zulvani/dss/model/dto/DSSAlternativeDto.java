package com.zulvani.dss.model.dto;

import com.zulvani.dss.model.entity.DSSAlternative;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DSSAlternativeDto {
    private Long id;
    private List<DSSParameterValueDto> parameterValues;
    private String name;
    private Long projectId;
    private Instant deletedAt;

    public DSSAlternativeDto toDto(DSSAlternative alternative){
        if (alternative == null) {
            return this;
        }

        setName(alternative.getName());
        setParameterValues(alternative.getParameterValues());
        setId(alternative.getId());
        setProjectId(alternative.getProjectId());
        setDeletedAt(alternative.getDeletedAt());
        return this;
    }

    public DSSAlternative toEntity(){
        DSSAlternative entity = DSSAlternative.builder().build();
        entity.setName(getName());
        entity.setParameterValues(getParameterValues());
        entity.setProjectId(getProjectId());
        entity.setId(getId());
        entity.setDeletedAt(getDeletedAt());
        return entity;
    }
}
