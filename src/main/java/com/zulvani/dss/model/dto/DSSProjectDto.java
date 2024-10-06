package com.zulvani.dss.model.dto;

import com.zulvani.dss.model.entity.DSSProject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DSSProjectDto {
    private Long id;
    private List<DSSParameterDto> parameters;
    private String projectName;
    private String description;

    public DSSProjectDto map(DSSProject project){
        if (project == null) {
            return this;
        }

        setProjectName(project.getProjectName());
        setParameters(project.getParameters());
        setDescription(project.getDescription());
        setId(project.getId());
        return this;
    }
}
