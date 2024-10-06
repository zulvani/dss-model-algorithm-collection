package com.zulvani.dss.service;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.cons.DSSAlgorithm;
import com.zulvani.dss.model.dto.DSSAlternativeDto;
import com.zulvani.dss.model.dto.DSSParameterDto;
import com.zulvani.dss.model.dto.DSSParameterValueDto;
import com.zulvani.dss.model.dto.DSSProjectDto;
import com.zulvani.dss.model.entity.DSSAlternative;
import com.zulvani.dss.model.entity.DSSProject;
import com.zulvani.dss.model.request.CreateOrUpdateDSS;
import com.zulvani.dss.model.request.DSSRequest;
import com.zulvani.dss.model.response.SAWResponse;
import com.zulvani.dss.model.response.SearchDSSResult;
import com.zulvani.dss.repository.DSSAlternativeRepository;
import com.zulvani.dss.repository.DSSProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@Transactional(readOnly = true)
public class DSSService {
    @Autowired
    private SAWService sawService;

    @Autowired
    private WPService wpService;

    @Autowired
    private DSSProjectRepository dssProjectRepository;

    @Autowired
    private DSSAlternativeRepository dssAlternatifRepository;

    public SAWResponse execute(DSSRequest request) throws Exception{
        int parameterSize;

        parameterSize = request.getParameters().length;
        for(DSSAlternativeParameter ap : request.getDssAlternativeParameters()){
            if (ap.getParameterValues().length != parameterSize) {
                throw new Exception("There are one or more invalid parameter value");
            }
        }
        SAWResponse resp = null;
        if (request.getMethod().equals(DSSAlgorithm.SAW)) {
            resp = sawService.execute(request);
        } else if (request.getMethod().equals(DSSAlgorithm.WP)){
            resp = wpService.execute(request);
        }
        return resp;
    }

    public DSSProjectDto getProject(Long id) throws Exception{
        Optional<DSSProject> savedProject = dssProjectRepository.findById(id);
        if(savedProject.isEmpty()){
            throw new Exception("Project not found");
        }
        return DSSProjectDto.builder().build().toDto(savedProject.get());
    }

    public SearchDSSResult getProjects(){
        return SearchDSSResult.builder()
                .data(dssProjectRepository.search().stream().map(e -> DSSProjectDto.builder().build().toDto(e)).toList())
                .build();
    }

    //TODO
    @Transactional(readOnly = false)
    public DSSProjectDto createOrSaveDSS(CreateOrUpdateDSS request) throws Exception {
        boolean isCreateNew = false;
        if (null == request.getProject().getId()) {
            isCreateNew = true;
        }

        Long projectId = request.getProject().getId();
        Optional<DSSProject> optProject;
        DSSProject project = DSSProject.builder().build();
        if (projectId != null) {
            optProject = dssProjectRepository.findById(projectId);
            if (optProject.isEmpty()) {
                throw new Exception("Invalid project Id");
            }
            project = optProject.get();
        }

        project.setProjectName(request.getProject().getProjectName());
        project.setDescription(request.getProject().getDescription());

        for (DSSParameterDto p : request.getProject().getParameters()) {
            if (p.getCode() == null) {
                p.setCode(UUID.randomUUID().toString());
            }
        }
        project.setParameters(request.getProject().getParameters());
        DSSProject savedProject = dssProjectRepository.save(project);

        if (!isCreateNew) {
            List<DSSAlternative> existingAlternatives = dssAlternatifRepository.findByProjectId(projectId);
            for(DSSAlternative existing : existingAlternatives) {
                boolean found = false;
                for(DSSAlternativeDto newAlternative : request.getAlternatives()) {
                    if (existing.getId().equals(newAlternative.getId())) {
                        found = true;
                        break;
                    }
                }

                if(!found){
                    existing.setDeletedAt(Instant.now());
                    request.getAlternatives().add(DSSAlternativeDto.builder().build().toDto(existing));
                }
            }
        }

        if(isCreateNew){
            for(DSSAlternativeDto alt : request.getAlternatives()){
                int i = 0;
                for(DSSParameterValueDto pv : alt.getParameterValues()){
                    pv.setParameterCode(request.getProject().getParameters().get(i).getCode());
                    i++;
                }
            }
        }

        List<DSSAlternative> unsavedAlternatives = request.getAlternatives().stream().map(DSSAlternativeDto::toEntity).toList();
        unsavedAlternatives.forEach(e -> e.setProjectId(savedProject.getId()));
        dssAlternatifRepository.saveAll(unsavedAlternatives);

        List<DSSAlternative> savedAlternatives = dssAlternatifRepository.findByProjectId(savedProject.getId());
        DSSProjectDto projectSaved = DSSProjectDto.builder().build().toDto(savedProject);
        projectSaved.setAlternatives(
                savedAlternatives.stream().map(e ->
                        DSSAlternativeDto.builder().build().toDto(e)).toList());
        return projectSaved;
    }
}
