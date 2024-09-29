package com.zulvani.dss.controller;

import com.zulvani.dss.model.request.DSSRequest;
import com.zulvani.dss.model.response.SAWResponse;
import com.zulvani.dss.service.DSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private DSSService dssService;

    @CrossOrigin(origins = "*")

    @GetMapping("/info")
    public String hi(){
        return "dss-v1.0.0";
    }

    //TODO
    @PostMapping("/dss")
    public ResponseEntity<SAWResponse> dssSAW(@RequestBody DSSRequest dssRequest) throws Exception {
        SAWResponse resp = dssService.execute(dssRequest);
        return ResponseEntity.ok().body(resp);
    }

}
