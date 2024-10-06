package com.zulvani.area.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Area extends BaseRepository {

    @NotEmpty(message = "Please enter area code")
    @Column(name = "area_code")
    private String areaCode;

    @NotEmpty(message = "Please enter area name")
    @Column
    private String name;

    public Area() {
    }

    public Area(@NotEmpty(message = "Please enter area code") String areaCode, @NotEmpty(message = "Please enter area name") String name) {
        this.areaCode = areaCode;
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
