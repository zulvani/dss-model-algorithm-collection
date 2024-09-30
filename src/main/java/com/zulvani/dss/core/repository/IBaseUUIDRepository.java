package com.zulvani.dss.core.repository;

import java.util.Date;
import java.util.UUID;

public interface IBaseUUIDRepository<T> {
    UUID getId();
    void setId(UUID id);
    Date getCreated();
    void setCreated(Date created);
    Date getLastUpdated();
    void setLastUpdated(Date lastUpdated);
    Long getCreatedBy();
    void setCreatedBy(Long createdBy);
    Long getLastUpdatedBy();
    void setLastUpdatedBy(Long lastUpdatedBy);
    Date getDeletedAt();
    void setDeletedAt(Date deletedAt);
    Long getDeletedBy();
    void setDeletedBy(Long deletedBy);
    default void setCreatedById(String s){}
    default void setLastUpdatedById(String s){}
    default void setDeletedById(String s){}
    default String getCreatedById(){return null;}
    default String getLastUpdatedById(){return null;}
    default String getDeletedById(){return null;}
    default <T> Object toDto(){return null;};
}
