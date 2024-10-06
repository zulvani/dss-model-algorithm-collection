package com.zulvani.dss.repository;

import com.zulvani.dss.model.entity.DSSAlternative;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DSSAlternativeRepository extends CrudRepository<DSSAlternative, Long> {

    @Query("FROM DSSAlternative WHERE projectId = :projectId AND deletedAt IS NULL ORDER BY ID ASC")
    List<DSSAlternative> findByProjectId(Long projectId);

}
