package com.zulvani.area.model;

import java.time.Instant;

public interface IBaseDto<T> {
    Long getId();
    void setId(Long id);
    Instant getCreated();
    void setCreated(Instant created);
    Instant getLastUpdated();
    void setLastUpdated(Instant lastUpdated);
    Long getCreatedBy();
    void setCreatedBy(Long createdBy);
    Long getLastUpdatedBy();
    void setLastUpdatedBy(Long lastUpdatedBy);
    Instant getDeletedAt();
    void setDeletedAt(Instant deletedAt);
    Long getDeletedBy();
    void setDeletedBy(Long deletedBy);
    default void setCreatedById(String s){}
    default void setLastUpdatedById(String s){}
    default void setDeletedById(String s){}
    default String getCreatedById(){return null;}
    default String getLastUpdatedById(){return null;}
    default String getDeletedById(){return null;}
}
