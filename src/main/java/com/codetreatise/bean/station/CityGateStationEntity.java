package com.codetreatise.bean.station;


import com.codetreatise.bean.base.BaseEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
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
    public Double nominalCapacity;
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


    @ManyToMany(fetch = FetchType.LAZY)
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

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(
            name="station_run"
            , joinColumns={
            @JoinColumn(name="station_id", nullable=false)
    }
            , inverseJoinColumns={
            @JoinColumn(name="run_id", nullable=false)
    })
    public List<RunEntity> runs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gas_id")
    private GasEntity gasEntity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "condition_id")
    private ConditionEntity condition;

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

    public Double getNominalCapacity() {
        return nominalCapacity;
    }

    public void setNominalCapacity(Double nominalCapacity) {
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
