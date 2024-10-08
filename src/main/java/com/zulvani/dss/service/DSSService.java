package com.zulvani.dss.service;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.DSSWeight;
import com.zulvani.dss.model.cons.DSSAlgorithm;
import com.zulvani.dss.model.cons.DSSCriteria;
import com.zulvani.dss.model.cons.DSSWeightMethod;
import com.zulvani.dss.model.dto.DSSAlternativeDto;
import com.zulvani.dss.model.dto.DSSParameterDto;
import com.zulvani.dss.model.dto.DSSParameterValueDto;
import com.zulvani.dss.model.dto.DSSProjectDto;
import com.zulvani.dss.model.entity.DSSAlternative;
import com.zulvani.dss.model.entity.DSSProject;
import com.zulvani.dss.model.request.CreateOrUpdateDSS;
import com.zulvani.dss.model.request.DSSRequest;
import com.zulvani.dss.model.request.RunDSSRequest;
import com.zulvani.dss.model.response.SAWResponse;
import com.zulvani.dss.model.response.SearchDSSResult;
import com.zulvani.dss.repository.DSSAlternativeRepository;
import com.zulvani.dss.repository.DSSProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
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

    public SAWResponse run(RunDSSRequest req) throws Exception{

        Optional<DSSProject> projectOpt = dssProjectRepository.searchById(req.getProjectId());
        if (projectOpt.isEmpty()) {
            throw new Exception("Project not found");
        }

        DSSProject project = projectOpt.get();

        BigDecimal weightValues[] = new BigDecimal[req.getParameters().size()];
        int i = 0;
        for(DSSParameterDto p : req.getParameters()){
            weightValues[i] = p.getWeight();
            i++;
        }

        List<DSSAlternative> alternatives = dssAlternatifRepository.findByProjectId(project.getId());

        List<DSSAlternativeParameter> alternativeParameters = new ArrayList<>();
        for(DSSAlternative al : alternatives) {
            BigDecimal alv[] = new BigDecimal[al.getParameterValues().size()];
            int j = 0;
            for(DSSParameterValueDto dd : al.getParameterValues()) {
                alv[j] = dd.getParameterValue();
                j++;
            }
            DSSAlternativeParameter a = DSSAlternativeParameter.builder()
                    .alternativeName(al.getName())
                    .parameterValues(alv)
                    .build();
            alternativeParameters.add(a);
        }

        DSSCriteria criterias[] = new DSSCriteria[project.getParameters().size()];
        String params[] = new String[project.getParameters().size()];
        int x = 0;
        for(DSSParameterDto p : project.getParameters()) {
            criterias[x] = p.getCriteria();
            params[x] = p.getName();
            x++;
        }

        DSSRequest request = DSSRequest.builder()
                .method(project.getDssMethod())
                .parameters(params)
                .weight(DSSWeight.builder()
                        .values(weightValues)
                        .method(DSSWeightMethod.DIRECT)
                        .build())
                .dssAlternativeParameters(alternativeParameters)
                .criteria(criterias)
                .build();

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
        Optional<DSSProject> savedProject = dssProjectRepository.searchById(id);
        if(savedProject.isEmpty()){
            throw new Exception("Project not found");
        }

        List<DSSAlternative> alternatives = dssAlternatifRepository.findByProjectId(savedProject.get().getId());
        DSSProjectDto dto = DSSProjectDto.builder().build().toDto(savedProject.get());

        for(DSSAlternative alt : alternatives) {
            for(DSSParameterValueDto vp : alt.getParameterValues()) {
                for(DSSParameterDto param : dto.getParameters()){
                    if (param.getCode().equals(vp.getParameterCode())) {
                        vp.setParameterName(param.getName());
                    }
                }
            }
        }

        dto.setAlternatives(alternatives.stream().map(e -> DSSAlternativeDto.builder().build().toDto(e)).toList());
        return dto;
    }

    @Transactional(readOnly = false)
    public DSSProjectDto deleteProject(Long id) throws Exception{
        Optional<DSSProject> savedProject = dssProjectRepository.searchById(id);
        if(savedProject.isEmpty()){
            throw new Exception("Project not found");
        }
        DSSProject willBeDeletedProject = savedProject.get();
        willBeDeletedProject.setDeletedAt(Instant.now());
        DSSProject deletedProject = dssProjectRepository.save(willBeDeletedProject);
        return DSSProjectDto.builder().build().toDto(deletedProject);
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

        project.setDssMethod(request.getProject().getDssMethod());
        project.setProjectName(request.getProject().getProjectName());
        project.setDescription(request.getProject().getDescription());

        // PARAMETER
        if (request.getProject().getParameters() != null) {
            for (DSSParameterDto p : request.getProject().getParameters()) {
                if (p.getCode() == null) {
                    p.setCode(UUID.randomUUID().toString());
                }
            }
            project.setParameters(request.getProject().getParameters());
        }
        DSSProject savedProject = dssProjectRepository.save(project);

        // ALTERNATIVE
        if (!isCreateNew) {
            if (request.getAlternatives() != null) {
                List<DSSAlternative> existingAlternatives = dssAlternatifRepository.findByProjectId(projectId);
                for (DSSAlternative existing : existingAlternatives) {
                    boolean found = false;
                    for (DSSAlternativeDto newAlternative : request.getAlternatives()) {
                        if (existing.getId().equals(newAlternative.getId())) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        existing.setDeletedAt(Instant.now());
                        request.getAlternatives().add(DSSAlternativeDto.builder().build().toDto(existing));
                    }
                }
            }
        }

        if(isCreateNew){
            if (request.getAlternatives() != null) {
                for (DSSAlternativeDto alt : request.getAlternatives()) {
                    int i = 0;
                    for (DSSParameterValueDto pv : alt.getParameterValues()) {
                        pv.setParameterCode(request.getProject().getParameters().get(i).getCode());
                        i++;
                    }
                }
            }
        }

        DSSProjectDto projectSaved = DSSProjectDto.builder().build().toDto(savedProject);

        if (request.getAlternatives() != null) {
            List<DSSAlternative> unsavedAlternatives = request.getAlternatives().stream().map(DSSAlternativeDto::toEntity).toList();
            unsavedAlternatives.forEach(e -> e.setProjectId(savedProject.getId()));
            dssAlternatifRepository.saveAll(unsavedAlternatives);

            List<DSSAlternative> savedAlternatives = dssAlternatifRepository.findByProjectId(savedProject.getId());
            projectSaved.setAlternatives(
                    savedAlternatives.stream().map(e ->
                            DSSAlternativeDto.builder().build().toDto(e)).toList());
        }
        return projectSaved;
    }
}
