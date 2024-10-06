package com.zulvani.area;

import com.zulvani.area.model.AreaDto;
import com.zulvani.area.service.AreaService;
import com.zulvani.dss.core.model.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping("/code/{areaCode}")
    public ResponseEntity<BaseResponse> getArea(@PathVariable("areaCode") String areaCode) {
        AreaDto area = areaService.getArea(areaCode);
        BaseResponse baseResponse = BaseResponse.builder()
                .data(area)
                .build();
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping()
    public ResponseEntity<BaseResponse> searchArea(@RequestParam("name") String areaName) {
        List<AreaDto> area = areaService.search(areaName);
        BaseResponse baseResponse = BaseResponse.builder()
                .data(area)
                .build();
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/provinces")
    public ResponseEntity<BaseResponse> getAllProvinces() {
        List<AreaDto> area = areaService.getProvinces();
        BaseResponse baseResponse = BaseResponse.builder()
                .data(area)
                .build();
        return ResponseEntity.ok(baseResponse);
    }
}
