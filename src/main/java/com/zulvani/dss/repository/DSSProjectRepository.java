package com.zulvani.dss.repository;

import com.zulvani.dss.model.entity.DSSProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DSSProjectRepository extends CrudRepository<DSSProject, Long> {

    @Query("FROM DSSProject ORDER BY ID DESC")
    List<DSSProject> search();

}
