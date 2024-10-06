package com.zulvani.area.service;

import com.zulvani.area.mapper.AreaMapper;
import com.zulvani.area.model.Area;
import com.zulvani.area.model.AreaDto;
import com.zulvani.area.repository.AreaRepository;
import com.zulvani.dss.core.service.BaseServiceImpl;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class AreaServiceImpl extends BaseServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    AreaMapper mapper;

    @Transactional(readOnly = false)
    @Override
    public Area save(Area area) throws PersistenceException {
        if(area == null){
            throw new PersistenceException("Please enter area");
        }

        return areaRepository.save(area);
    }

    @Transactional(readOnly = false, rollbackFor = PersistenceException.class)
    @Override
    public Area remove(Area area) throws PersistenceException {
        if(area == null){
            throw new PersistenceException("Please enter area which want to delete");
        }
        area.setDeletedAt(Instant.now());
        return areaRepository.save(area);
    }

    @Transactional(readOnly = false, rollbackFor = PersistenceException.class)
    @Override
    public Area removeById(long id) throws PersistenceException {
        Optional<Area> a = areaRepository.findById(id);
        if(a == null || (a != null && a.get() == null)){
            throw new PersistenceException("Area ID: " + id + " not found!!!");
        }
        return remove(a.get());
    }

    @Override
    public AreaDto getArea(String areaCode) {
        List<Area> areas = areaRepository.findByAreaCodeAndDeletedAtIsNull(areaCode);
        return areas.size() > 0 ? mapper.map(areas.get(0)) : null;
    }

    @Override
    public List<AreaDto> search(String areaName) {
        List<Area> areas = areaRepository.findByName("%" + areaName + "%");
        return areas.stream().map(e -> mapper.map(e)).collect(Collectors.toList());
    }

    @Override
    public List<AreaDto> getProvinces() {
        return areaRepository.findAllProvinces().stream().map(e -> mapper.map(e)).collect(Collectors.toList());
    }
}
