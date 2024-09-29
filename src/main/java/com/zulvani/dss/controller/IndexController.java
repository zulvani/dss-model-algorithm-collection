package com.zulvani.dss.controller;

import com.zulvani.dss.service.DSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private DSSService dssService;

    @GetMapping
    public String hi(){
        return "index of dss-v1.0.0";
    }

}
