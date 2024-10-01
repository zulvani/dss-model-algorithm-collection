package com.zulvani.dss.controller;

import com.zulvani.dss.core.model.BaseResponse;
import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.DSSParameter;
import com.zulvani.dss.model.DSSWeight;
import com.zulvani.dss.model.cons.DSSAlgorithm;
import com.zulvani.dss.model.cons.DSSCriteria;
import com.zulvani.dss.model.cons.DSSWeightMethod;
import com.zulvani.dss.model.request.DSSRequest;
import com.zulvani.dss.model.request.DSSSpecificRequest;
import com.zulvani.dss.model.response.DSSResponse;
import com.zulvani.dss.model.response.SAWResponse;
import com.zulvani.dss.service.DSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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

    @GetMapping("/dss/{name}")
    public ResponseEntity<BaseResponse> getDss(@PathVariable("name") String name) throws Exception {
        DSSResponse dssResponse = DSSResponse.builder()
                .parameters(List.of(
                        DSSParameter.builder().code("P1").name("Jangka Waktu").build(),
                        DSSParameter.builder().code("P2").name("Return Value").build(),
                        DSSParameter.builder().code("P3").name("Tingkat Likuiditas").build(),
                        DSSParameter.builder().code("P4").name("Modal").build(),
                        DSSParameter.builder().code("P5").name("Pajak").build(),
                        DSSParameter.builder().code("P6").name("Tingkat Resiko").build()
                        )
                ).build();
        BaseResponse response = BaseResponse.builder().data(dssResponse).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/dss/{name}")
    public ResponseEntity<SAWResponse> postDss(@PathVariable("name") String name, @RequestBody DSSSpecificRequest request) throws Exception {
        DSSRequest dss = DSSRequest.builder()
                .method(DSSAlgorithm.SAW)
                .criteria(new DSSCriteria[]{
                        DSSCriteria.DSS_CRITERIA_COST,
                        DSSCriteria.DSS_CRITERIA_BENEFIT,
                        DSSCriteria.DSS_CRITERIA_BENEFIT,
                        DSSCriteria.DSS_CRITERIA_COST,
                        DSSCriteria.DSS_CRITERIA_COST,
                        DSSCriteria.DSS_CRITERIA_COST})
                .weight(DSSWeight.builder()
                        .method(DSSWeightMethod.DIRECT)
                        .values(request.getParameters().stream()
                                .map(DSSParameter::getAmount)
                                .toArray(BigDecimal[]::new))
                        .build())
                .parameters(request.getParameters().stream().map(DSSParameter::getName).toArray(String[]::new))
                .dssAlternativeParameters(List.of(
                        DSSAlternativeParameter.builder()
                                .alternativeName("Saham")
                                .parameterValues(new BigDecimal[]{
                                        BigDecimal.valueOf(70), // Jangka Waktu
                                        BigDecimal.valueOf(40), // Return Value
                                        BigDecimal.valueOf(60), // Tingkat Likuiditas
                                        BigDecimal.valueOf(10), // Modal
                                        BigDecimal.valueOf(20), // Pajak
                                        BigDecimal.valueOf(70), // Tingkat Resiko
                                }).build(),
                        DSSAlternativeParameter.builder()
                                .alternativeName("Emas")
                                .parameterValues(new BigDecimal[]{
                                        BigDecimal.valueOf(90), // Jangka Waktu
                                        BigDecimal.valueOf(35), // Return Value
                                        BigDecimal.valueOf(90), // Tingkat Likuiditas
                                        BigDecimal.valueOf(35), // Modal
                                        BigDecimal.valueOf(15), // Pajak
                                        BigDecimal.valueOf(10), // Tingkat Resiko
                                }).build(),
                        DSSAlternativeParameter.builder()
                                .alternativeName("Properti")
                                .parameterValues(new BigDecimal[]{
                                        BigDecimal.valueOf(90), // Jangka Waktu
                                        BigDecimal.valueOf(95), // Return Value
                                        BigDecimal.valueOf(10), // Tingkat Likuiditas
                                        BigDecimal.valueOf(95), // Modal
                                        BigDecimal.valueOf(40), // Pajak
                                        BigDecimal.valueOf(10), // Tingkat Resiko
                                }).build(),
                        DSSAlternativeParameter.builder()
                                .alternativeName("Pendidikan")
                                .parameterValues(new BigDecimal[]{
                                        BigDecimal.valueOf(70), // Jangka Waktu
                                        BigDecimal.valueOf(70), // Return Value
                                        BigDecimal.valueOf(30), // Tingkat Likuiditas
                                        BigDecimal.valueOf(50), // Modal
                                        BigDecimal.valueOf(5), // Pajak
                                        BigDecimal.valueOf(10), // Tingkat Resiko
                                }).build()
                ))
                .build();

        SAWResponse resp = dssService.execute(dss);
        return ResponseEntity.ok().body(resp);
    }

}
