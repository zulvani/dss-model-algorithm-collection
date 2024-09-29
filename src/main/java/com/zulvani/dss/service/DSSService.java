package com.zulvani.dss.service;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.cons.DSSAlgorithm;
import com.zulvani.dss.model.request.DSSRequest;
import com.zulvani.dss.model.response.SAWResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DSSService {
    @Autowired
    private SAWService sawService;

    @Autowired
    private WPService wpService;

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
}
