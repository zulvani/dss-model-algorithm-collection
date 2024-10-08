package com.zulvani.dss.controller;

import com.zulvani.dss.core.model.BaseResponse;
import com.zulvani.dss.model.dto.DSSProjectDto;
import com.zulvani.dss.model.request.CreateOrUpdateDSS;
import com.zulvani.dss.model.request.RunDSSRequest;
import com.zulvani.dss.model.response.SAWResponse;
import com.zulvani.dss.service.DSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dss")
public class DSSController {

    @Autowired
    private DSSService dssService;

    @PostMapping()
    public ResponseEntity<BaseResponse> createOrSaveDSS(@RequestBody CreateOrUpdateDSS request) throws Exception {
        DSSProjectDto savedDSS = dssService.createOrSaveDSS(request);
        return ResponseEntity.ok().body(BaseResponse.builder().data(savedDSS).build());
    }

    @PostMapping("/run")
    public ResponseEntity<SAWResponse> createOrSaveDSS(@RequestBody RunDSSRequest request) throws Exception {
        SAWResponse resp = dssService.run(request);
        return ResponseEntity.ok().body(resp);
    }

    //TODO: custom exception to differentiate between internal server error vs not found
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getDetailDSS(@PathVariable("id") Long id) throws Exception {
        try {
            DSSProjectDto savedDSS = dssService.getProject(id);
            BaseResponse resp = BaseResponse.builder()
                    .data(savedDSS)
                    .build();
            return ResponseEntity.ok().body(resp);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteDSS(@PathVariable("id") Long id) throws Exception {
        try {
            DSSProjectDto savedDSS = dssService.deleteProject(id);
            BaseResponse resp = BaseResponse.builder()
                    .data(savedDSS)
                    .build();
            return ResponseEntity.ok().body(resp);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<BaseResponse> getDSS() throws Exception {
        BaseResponse resp = BaseResponse.builder()
                .data(dssService.getProjects())
                .build();
        return ResponseEntity.ok().body(resp);
    }
}