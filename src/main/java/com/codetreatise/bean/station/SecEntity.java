package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sec")
public class SecEntity extends BaseEntity {

    @OneToMany
    @JoinColumn(name = "sec_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ConditionEntity> conditions;

//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="station_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CityGateStationEntity station;

//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate time;

    @Size(max = 255)
    private String name;


    @Lob
    @Column(name="file")
    private byte[] excelFile;

    @Lob
    @Column(name = "file_location")
    private String fileLocation;

    @Lob
    @Column(name = "file_name")
    private String fileName;

    public byte[] getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(byte[] excelFile) {
        this.excelFile = excelFile;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<ConditionEntity> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionEntity> conditions) {
        this.conditions = conditions;
    }

    public CityGateStationEntity getStation() {
        return station;
    }

    public void setStation(CityGateStationEntity station) {
        this.station = station;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
