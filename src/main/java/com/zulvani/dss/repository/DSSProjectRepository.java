package com.zulvani.dss.repository;

import com.zulvani.dss.model.entity.DSSProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DSSProjectRepository extends CrudRepository<DSSProject, Long> {

    @Query("FROM DSSProject WHERE deletedAt IS NULL ORDER BY id DESC")
    List<DSSProject> search();

    @Query("FROM DSSProject WHERE id =:id AND deletedAt IS NULL")
    Optional<DSSProject> searchById(Long id);

}
