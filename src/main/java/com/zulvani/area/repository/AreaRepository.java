package com.zulvani.area.repository;

import com.zulvani.area.model.Area;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends CrudRepository<Area, Long> {
    List<Area> findByAreaCodeAndDeletedAtIsNull(String areaCode);
    @Query("FROM Area WHERE name LIKE :areaName AND deletedAt IS NULL")
    List<Area> findByName(String areaName);

    @Query("FROM Area WHERE LENGTH(areaCode) = 2")
    List<Area> findAllProvinces();
}
