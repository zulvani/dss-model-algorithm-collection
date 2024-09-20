package com.zulvani.dss.controller;

import com.zulvani.dss.model.request.DSSRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    //TODO
    @PostMapping("/dss/saw")
    public ResponseEntity<Object> dssSAW(@RequestBody DSSRequest dssRequest){
        return ResponseEntity.ok().body(dssRequest);
    }

}
