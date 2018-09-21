package com.codetreatise.bean.station;


import com.codetreatise.bean.base.BaseEntity;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "city_gate_station")
public class CityGateStationEntity extends BaseEntity {
    @Size(max = 255)
    public String province;
    @Size(max = 255)
    public String city;

    @Size(max = 255)
    public String region;
    @Size(max = 10)
    public String nominalCapacity;
    @Lob
    public String address;

    @ManyToOne
    @JoinColumn(name = "after_heater")
    public PipeSpecificationsEntity afterHeater;

    @ManyToOne
    @JoinColumn(name = "before_heater")
    public PipeSpecificationsEntity beforeHeater;

    @ManyToOne
    @JoinColumn(name = "collector")
    public PipeSpecificationsEntity collector;


    @ManyToMany
    @JoinTable(
            name="station_heater"
            , joinColumns={
            @JoinColumn(name="station_id", nullable=false)
    }
            , inverseJoinColumns={
            @JoinColumn(name="heater_id", nullable=false)
    })
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<HeaterEntity> heaters;

    @ManyToMany
    @JoinTable(
            name="station_run"
            , joinColumns={
            @JoinColumn(name="station_id", nullable=false)
    }
            , inverseJoinColumns={
            @JoinColumn(name="run_id", nullable=false)
    })
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<RunEntity> runs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gas_id")
    private GasEntity gasEntity;

    @OneToOne(fetch = FetchType.EAGER,orphanRemoval = true)
    @Cascade(value = { org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "condition_id")
    @Fetch(FetchMode.SELECT)
    private ConditionEntity condition;

    @OneToMany/*(cascade = {CascadeType.PERSIST, CascadeType.ALL})*/
    @JoinColumn(name = "station_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SecEntity> sec;

    public List<SecEntity> getSec() {
        return sec;
    }

    public void setSec(List<SecEntity> sec) {
        this.sec = sec;
    }

    public List<RunEntity> getRuns() {
        return runs;
    }

    public void setRuns(List<RunEntity> runs) {
        this.runs = runs;
    }

    public ConditionEntity getCondition() {
        return condition;
    }

    public void setCondition(ConditionEntity condition) {
        this.condition = condition;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNominalCapacity() {
        return nominalCapacity;
    }

    public void setNominalCapacity(String nominalCapacity) {
        this.nominalCapacity = nominalCapacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PipeSpecificationsEntity getAfterHeater() {
        return afterHeater;
    }

    public void setAfterHeater(PipeSpecificationsEntity afterHeater) {
        this.afterHeater = afterHeater;
    }

    public PipeSpecificationsEntity getBeforeHeater() {
        return beforeHeater;
    }

    public void setBeforeHeater(PipeSpecificationsEntity beforeHeater) {
        this.beforeHeater = beforeHeater;
    }


    public PipeSpecificationsEntity getCollector() {
        return collector;
    }

    public void setCollector(PipeSpecificationsEntity collector) {
        this.collector = collector;
    }


    public List<HeaterEntity> getHeaters() {
        return heaters;
    }

    public void setHeaters(List<HeaterEntity> heaters) {
        this.heaters = heaters;
    }

    public GasEntity getGasEntity() {
        return gasEntity;
    }

    public void setGasEntity(GasEntity gasEntity) {
        this.gasEntity = gasEntity;
    }
}
