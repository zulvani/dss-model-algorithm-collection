package com.zulvani.dss.service;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.cons.DSSAlgorithm;
import com.zulvani.dss.model.dto.DSSParameterDto;
import com.zulvani.dss.model.dto.DSSProjectDto;
import com.zulvani.dss.model.entity.DSSProject;
import com.zulvani.dss.model.request.CreateOrUpdateDSS;
import com.zulvani.dss.model.request.DSSRequest;
import com.zulvani.dss.model.response.SAWResponse;
import com.zulvani.dss.model.response.SearchDSSResult;
import com.zulvani.dss.repository.DSSTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DSSService {
    @Autowired
    private SAWService sawService;

    @Autowired
    private WPService wpService;

    @Autowired
    private DSSTopicRepository dssTopicRepository;

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
        Optional<DSSProject> savedProject = dssTopicRepository.findById(id);
        if(savedProject.isEmpty()){
            throw new Exception("Project not found");
        }
        return DSSProjectDto.builder().build().map(savedProject.get());
    }

    public SearchDSSResult getProjects(){
        return SearchDSSResult.builder()
                .data(dssTopicRepository.search().stream().map(e -> DSSProjectDto.builder().build().map(e)).toList())
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
            optProject = dssTopicRepository.findById(projectId);
            if (optProject.isEmpty()) {
                throw new Exception("Invalid project Id");
            }
            project = optProject.get();
        }

        project.setProjectName(request.getProject().getProjectName());
        project.setDescription(request.getProject().getDescription());

        long max = 1;
        if (!isCreateNew) {
            for (DSSParameterDto p : request.getProject().getParameters()) {
                if (p.getId() != null && p.getId() > max) {
                    max = p.getId();
                }
            }
        }

        for (DSSParameterDto p : request.getProject().getParameters()) {
            if (p.getId() == null) {
                p.setId(max);
                max++;
            }
        }
        project.setParameters(request.getProject().getParameters());
//
//        if (!isCreateNew) {
//            List<DSSParameter> existingParameters = dssParameterRepository.findByTopicId(topicId);
//            for(DSSParameter existing : existingParameters) {
//                boolean found = false;
//                for(DSSParameter newParam : parameters) {
//                    if (existing.getId().equals(newParam.getId())) {
//                        found = true;
//                        break;
//                    }
//                }
//
//                if(!found){
//                    existing.setDeletedAt(Instant.now());
//                    parameters.add(existing);
//                }
//            }
//        }
//
//        Iterable<DSSParameter> savedParameters = dssParameterRepository.saveAll(parameters);
//        List<DSSParameter> savedParams = StreamSupport.stream(savedParameters.spliterator(), false).toList();
//        return DSSProjectDto.builder()
//                .topic(DSSTopicDto.builder()
//                        .id(savedTopic.getId())
//                        .description(savedTopic.getDescription())
//                        .topicName(savedTopic.getTopicName())
//                        .build())
//                .parameters(savedParams.stream().map(e -> DSSParameterDto.builder()
//                        .name(e.getName())
//                        .id(e.getId())
//                        .build()).toList())
//                .build();
        DSSProject savedProject = dssTopicRepository.save(project);
        return DSSProjectDto.builder().build().map(savedProject);
    }
}
