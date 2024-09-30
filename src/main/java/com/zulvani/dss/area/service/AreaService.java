package com.zulvani.dss.area.service;

import com.zulvani.dss.area.model.Area;
import com.zulvani.dss.area.model.AreaDto;
import jakarta.persistence.PersistenceException;

import java.util.List;

public interface AreaService {
    Area save(Area area) throws PersistenceException;

    Area remove(Area area) throws PersistenceException;

    Area removeById(long id) throws PersistenceException;

    AreaDto getArea(String areaCode);

    List<AreaDto> search(String areaName);

    List<AreaDto> getProvinces();
}
