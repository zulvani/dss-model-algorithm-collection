package com.zulvani.dss.model.entity;

import com.zulvani.area.model.BaseRepository;
import com.zulvani.dss.model.dto.DSSParameterDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dss_project")
public class DSSProject extends BaseRepository {

    @Column
    private String projectName;

    @Column
    private String description;

    @Column
    @JdbcTypeCode(SqlTypes.JSON)
    private List<DSSParameterDto> parameters;
}
