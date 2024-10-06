package com.zulvani.dss.model.entity;

import com.zulvani.area.model.BaseRepository;
import com.zulvani.dss.model.dto.DSSParameterValueDto;
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
@Table(name = "dss_alternatif")
public class DSSAlternative extends BaseRepository {

    @Column
    private String name;

    @Column
    private Long projectId;

    @Column
    @JdbcTypeCode(SqlTypes.JSON)
    private List<DSSParameterValueDto> parameterValues;
}
